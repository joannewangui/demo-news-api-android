package wizag.com.kenyabuzz.SourceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 03/06/2018.
 */

public class Result {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private List<ResultSource> sources = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultSource> getSources() {
        return sources;
    }

    public void setSources(List<ResultSource> sources) {
        this.sources = sources;
    }
}
