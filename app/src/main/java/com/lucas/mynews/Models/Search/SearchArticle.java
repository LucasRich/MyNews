package com.lucas.mynews.Models.Search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchArticle {

    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;


    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }
}
