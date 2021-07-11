package com.cl.search.api;

import com.cl.search.model.SearchModel;
import com.cl.search.model.SearchResult;

public interface ISearchBaseApiService {

	SearchResult getWebSearchResult(SearchModel searchModel);
}
