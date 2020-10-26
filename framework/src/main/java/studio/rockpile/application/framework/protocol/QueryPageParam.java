package studio.rockpile.application.framework.protocol;

import java.io.Serializable;

public class QueryPageParam<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pagination page;
	private T query;

	@Override
	public String toString() {
		return "PageQueryParam [page=" + page + ", query=" + query + "]";
	}

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}

}
