package technical.test.renderer.builder;

import org.springframework.web.util.UriBuilder;

import java.net.URI;

public class QueryParamBuilder {
    private final UriBuilder uriBuilder;

    private QueryParamBuilder(UriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder;
    }

    public static QueryParamBuilder from(UriBuilder uriBuilder) {
        return new QueryParamBuilder(uriBuilder);
    }

    public QueryParamBuilder path(String path) {
        uriBuilder.path(path);
        return this;
    }

    public QueryParamBuilder addIfNotNull(String name, Object value) {
        if (value != null) {
            uriBuilder.queryParam(name, value);
        }
        return this;
    }

    public QueryParamBuilder addIfNotBlank(String name, String value) {
        if (value != null && !value.isBlank()) {
            uriBuilder.queryParam(name, value);
        }
        return this;
    }

    public URI build() {
        return uriBuilder.build();
    }
}
