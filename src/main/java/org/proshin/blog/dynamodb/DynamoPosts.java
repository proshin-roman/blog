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
import java.util.Optional;
import lombok.NonNull;
import org.proshin.blog.Url;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.PersistentPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoPosts implements PersistentPosts {

    public static final String TABLE_NAME = "blog_post";

    private final Table table;

    @Autowired
    public DynamoPosts(@NonNull DynamoDB dynamoDB) {
        this.table = dynamoDB.getTable(TABLE_NAME);
    }

    @NonNull
    @Override
    public Optional<PersistentPost> postByUrl(@NonNull Url url) {
        return Optional
                .ofNullable(table.getItem(new PrimaryKey("url", url.decoded())))
                .map(item -> new DynamoPost(table, item));
    }

    @NonNull
    @Override
    public List<PersistentPost> page(int offset, int count, boolean publishedOnly) {
        PageIterable<Item, ScanOutcome> pages =
                publishedOnly
                        ? table
                                .scan("published = :published",
                                        null,
                                        singletonMap(":published", true))
                                .pages()
                        : table
                                .scan()
                                .pages();

        List<PersistentPost> dynamoPosts = newArrayList();
        pages.forEach(page -> {
            page.forEach(item -> {
                dynamoPosts.add(new DynamoPost(table, item));
            });
        });
        return dynamoPosts;
    }

    @NonNull
    @Override
    public PersistentPost newPost(@NonNull Url url, @NonNull String title, @NonNull String content) {
        return new DynamoPost(table, url, title, now(), now(), false, content)
                .persist();
    }

    public Table getTable() {
        return table;
    }
}
