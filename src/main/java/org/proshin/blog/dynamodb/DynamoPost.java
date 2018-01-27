package org.proshin.blog.dynamodb;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.proshin.blog.DateToString;
import org.proshin.blog.StringToDate;
import org.proshin.blog.Url;
import org.proshin.blog.model.PersistentPost;

public class DynamoPost implements PersistentPost {
    private final Table posts;
    private final Url url;
    private final String title;
    private final LocalDateTime creationDate;
    private final LocalDateTime publicationDate;
    private final boolean published;
    private final String content;

    public DynamoPost(@NonNull Table posts, @NonNull Item item) {
        this(posts,
                new Url(item.getString("url")),
                item.getString("title"),
                new StringToDate(item.getString("creation_date")).toLocalDateTime(),
                new StringToDate(item.getString("publication_date")).toLocalDateTime(),
                item.getBOOL("published"),
                item.getString("content"));
    }

    public DynamoPost(@NonNull Table posts, @NonNull Url url, @NonNull String title,
            @NonNull LocalDateTime creationDate, @NonNull LocalDateTime publicationDate, boolean published,
            @NonNull String content) {
        this.posts = posts;
        this.url = url;
        this.title = title;
        this.creationDate = creationDate;
        this.publicationDate = publicationDate;
        this.published = published;
        this.content = content;
    }

    @Override
    public Url url() {
        return url;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public LocalDateTime creationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime publicationDate() {
        return publicationDate;
    }

    @Override
    public boolean published() {
        return published;
    }

    @Override
    public String content() {
        return content;
    }

    @NonNull
    @Override
    public PersistentPost publish() {
        return new DynamoPost(posts, url, title, creationDate, now(), true, content);
    }

    @NonNull
    @Override
    public PersistentPost unpublish() {
        return new DynamoPost(posts, url, title, creationDate, publicationDate, false, content);
    }

    @NonNull
    @Override
    public PersistentPost persist() {
        posts.putItem(
                new Item()
                        .withPrimaryKey(new PrimaryKey("url", url.decoded()))
                        .with("title", title)
                        .with("creation_date", new DateToString(creationDate).toString())
                        .with("publication_date", new DateToString(publicationDate).toString())
                        .with("published", published)
                        .with("content", content));
        return new DynamoPost(posts,
                posts.getItem(new PrimaryKey("url", url.decoded())));
    }

    @NonNull
    @Override
    public PersistentPost update() {
        posts.updateItem(new PrimaryKey("url", url.decoded()),
                new AttributeUpdate("title").put(title),
                new AttributeUpdate("creation_date")
                        .put(new DateToString(creationDate).toString()),
                new AttributeUpdate("publication_date")
                        .put(new DateToString(publicationDate).toString()),
                new AttributeUpdate("published").put(published),
                new AttributeUpdate("content").put(content));
        return this;
    }

    @Override
    public void delete() {
        posts.deleteItem(new PrimaryKey("url", url.decoded()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        DynamoPost that = (DynamoPost) o;

        return new EqualsBuilder()
                .append(published, that.published)
                .append(url, that.url)
                .append(title, that.title)
                .append(creationDate, that.creationDate)
                .append(publicationDate, that.publicationDate)
                .append(content, that.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(url)
                .append(title)
                .append(creationDate)
                .append(publicationDate)
                .append(published)
                .append(content)
                .toHashCode();
    }
}
