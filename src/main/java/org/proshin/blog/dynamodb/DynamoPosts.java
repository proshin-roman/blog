package org.proshin.blog.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.internal.PageIterable;
import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singletonMap;
import java.util.List;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoPosts implements Posts {

    public static final String TABLE_NAME = "blog_post";

    private final Table posts;

    @Autowired
    public DynamoPosts(@NonNull DynamoDB dynamoDB) {
        this.posts = dynamoDB.getTable(TABLE_NAME);
    }

    @NonNull
    @Override
    public PersistentPost selectOne(@NonNull String id) throws PostNotFoundException {
        Item item = posts.getItem(new PrimaryKey("id", id));
        if (item != null) {
            return new DynamoPost(posts, item);
        }
        throw new PostNotFoundException(String.format("Post %s not found", id));
    }

    @NonNull
    @Override
    public List<PersistentPost> selectPage(int offset, int count, boolean publishedOnly) {
        PageIterable<Item, ScanOutcome> pages =
                publishedOnly
                        ? posts
                                .scan("published = :published",
                                        null,
                                        singletonMap(":published", true))
                                .pages()
                        : posts
                                .scan()
                                .pages();

        List<PersistentPost> dynamoPosts = newArrayList();
        pages.forEach(page -> {
            page.forEach(item -> {
                dynamoPosts.add(new DynamoPost(posts, item));
            });
        });
        return dynamoPosts;
    }

    @NonNull
    @Override
    public PersistentPost create(@NonNull String title, @NonNull String content) {
        return new DynamoPost(posts, title, now(), now(), false, content)
                .save();
    }

    @Override
    public void delete(@NonNull String id) {
        posts.deleteItem(new PrimaryKey("id", id));
    }

    public Table getTable() {
        return posts;
    }
}
