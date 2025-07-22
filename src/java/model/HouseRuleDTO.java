package model;

public class HouseRuleDTO {
    private int ruleId;
    private int propertyId;
    private String ruleText;

    public HouseRuleDTO() {}

    public HouseRuleDTO(int ruleId, int propertyId, String ruleText) {
        this.ruleId = ruleId;
        this.propertyId = propertyId;
        this.ruleText = ruleText;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getRuleText() {
        return ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }
}
