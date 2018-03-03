package nyc.c4q.medihow.model;

import java.util.List;

/**
 * Created by yokilam on 3/3/18.
 */

public class MedicareOffice {
    private String bbl;
    private String bin;
    private String census_tract;
    private String community_board;
    private String community_council;
    private String latitude;
    private String longitude;
    private String name_of_borough;
    private String name_of_medicaid_office;
    private String nta;
    private String office_address;
    private String phone_number;
    private String postcode;

    public String getBbl() {
        return bbl;
    }

    public String getBin() {
        return bin;
    }

    public String getCensus_tract() {
        return census_tract;
    }

    public String getCommunity_board() {
        return community_board;
    }

    public String getCommunity_council() {
        return community_council;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longitude;
    }

    public String getName_of_borough() {
        return name_of_borough;
    }

    public String getName_of_medical_office() {
        return name_of_medicaid_office;
    }

    public String getNta() {
        return nta;
    }

    public String getOffice_address() {
        return office_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPostcode() {
        return postcode;
    }
}

