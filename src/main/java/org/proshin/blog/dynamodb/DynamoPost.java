package org.proshin.blog.dynamodb;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.UUID;
import lombok.NonNull;
import org.proshin.blog.DateToString;
import org.proshin.blog.StringToDate;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPost;

public class DynamoPost implements PersistentPost {
    private final Table posts;
    private final String id;
    private final String title;
    private final LocalDateTime creationDate;
    private final LocalDateTime publicationDate;
    private final boolean published;
    private final String content;

    public DynamoPost(@NonNull Table posts, @NonNull Item item) {
        this(posts,
                item.getString("id"),
                item.getString("title"),
                new StringToDate(item.getString("creation_date")).toLocalDateTime(),
                new StringToDate(item.getString("publication_date")).toLocalDateTime(),
                item.getBOOL("published"),
                item.getString("content"));
    }

    public DynamoPost(@NonNull Table posts, @NonNull String title, @NonNull LocalDateTime creationDate,
            @NonNull LocalDateTime publicationDate, boolean published, @NonNull String content) {
        this(posts, null, title, creationDate, publicationDate, published, content);
    }

    public DynamoPost(@NonNull Table posts, String id, @NonNull String title, @NonNull LocalDateTime creationDate,
            @NonNull LocalDateTime publicationDate, boolean published, @NonNull String content) {
        this.posts = posts;
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.publicationDate = publicationDate;
        this.published = published;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    @Override
    public boolean isPublished() {
        return published;
    }

    @Override
    public String getContent() {
        return content;
    }

    @NonNull
    @Override
    public PersistentPost publish() throws PostNotFoundException {
        return new DynamoPost(posts, id, title, creationDate, now(), true, content);
    }

    @NonNull
    @Override
    public PersistentPost unpublish() throws PostNotFoundException {
        return new DynamoPost(posts, id, title, creationDate, publicationDate, false, content);
    }

    @NonNull
    @Override
    public PersistentPost save() {
        if (id != null) {
            posts.updateItem(new PrimaryKey("id", id),
                    new AttributeUpdate("title").put(title),
                    new AttributeUpdate("creation_date")
                            .put(new DateToString(creationDate).toString()),
                    new AttributeUpdate("publication_date")
                            .put(new DateToString(publicationDate).toString()),
                    new AttributeUpdate("published").put(published),
                    new AttributeUpdate("content").put(content));
            return this;
        } else {
            String generatedId = UUID.randomUUID().toString();

            posts.putItem(
                    new Item()
                            .withPrimaryKey(new PrimaryKey("id", generatedId))
                            .with("title", title)
                            .with("creation_date", new DateToString(creationDate).toString())
                            .with("publication_date", new DateToString(publicationDate).toString())
                            .with("published", published)
                            .with("content", content));
            return new DynamoPost(posts, posts.getItem(new PrimaryKey("id", generatedId)));
        }
    }
}
