package com.jun.plugin.solr.api;

import com.jun.plugin.solr.model.SearchModel;
import com.jun.plugin.solr.model.SearchResult;

public interface ISearchBaseApiService {

	SearchResult getWebSearchResult(SearchModel searchModel);
}
