package org.proshin.blog.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Page;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.common.collect.Iterables;
import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
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
        Page<Item, ScanOutcome> firstPage =
                publishedOnly
                        ? Iterables.getFirst(
                                posts.scan("published = :published",
                                        null,
                                        singletonMap(":published", true)).pages(),
                                null)
                        : Iterables.getFirst(
                                posts.scan().pages(),
                                null);
        if (firstPage == null) {
            return emptyList();
        }

        List<PersistentPost> dynamoPosts = newArrayList();
        for (Item item : firstPage) {
            dynamoPosts.add(new DynamoPost(posts, item));
        }
        return dynamoPosts;
    }

    @NonNull
    @Override
    public PersistentPost create() {
        return new DynamoPost(posts, "New article", now(), now(), false, "It's a draft")
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
