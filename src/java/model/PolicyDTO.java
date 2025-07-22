package model;

public class PolicyDTO {
    private int policyId;
    private String title;
    private String description;

    public PolicyDTO() {}

    public PolicyDTO(int policyId, String title, String description) {
        this.policyId = policyId;
        this.title = title;
        this.description = description;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
