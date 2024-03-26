package ba.unsa.etf.nbp.bookstorebackend.builder;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic select query builder
 */
public class SelectQueryBuilder {
    private static SelectQueryBuilder builder_;
    private static List<String> columns_ = new ArrayList<>();
    private static List<String> tableJoins_ = new ArrayList<>();
    private static List<String> predicates_ = new ArrayList<>();

    public SelectQueryBuilder() {}

    /**
     * Creates a new query builder
     * @return
     */
    public static SelectQueryBuilder create() {
        builder_ = new SelectQueryBuilder();
        return builder_;
    }

    public SelectQueryBuilder select(String... columns) {
        columns_.addAll(List.of(columns));
        return builder_;
    }

    public SelectQueryBuilder from(String... tableJoins) {
        tableJoins_.addAll(List.of(tableJoins));
        return builder_;
    }

    public SelectQueryBuilder where(String... predicates) {
        predicates_.addAll(List.of(predicates));
        return builder_;
    }

    public String build() {
        if (tableJoins_.isEmpty()) {
            throw new BuilderException("Cannot create a SELECT statement without from clause");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        if (CollectionUtils.isEmpty(columns_)) {
            sb.append("*");
        } else {
            sb.append(String.join(", ", columns_));
        }

        sb.append("\nFROM ");
        sb.append(String.join("\n", tableJoins_));

        if (!CollectionUtils.isEmpty(predicates_)) {
            sb.append("\nWHERE ");
            sb.append(String.join(" AND ", predicates_));
        }

        return sb.toString();
    }
}
