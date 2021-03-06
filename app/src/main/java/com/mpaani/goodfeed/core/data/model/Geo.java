package com.mpaani.goodfeed.core.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_GEO_LAT;
import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_GEO_LNG;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lat",
        "lng"
})
public class Geo {

    @ColumnInfo(name = USER_ADDRESS_GEO_LAT)
    @JsonProperty("lat")
    private String lat;

    @ColumnInfo(name = USER_ADDRESS_GEO_LNG)
    @JsonProperty("lng")
    private String lng;

    @Ignore
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public String getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
