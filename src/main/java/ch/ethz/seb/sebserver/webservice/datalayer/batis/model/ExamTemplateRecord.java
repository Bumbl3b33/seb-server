package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ExamTemplateRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.institution_id")
    private Long institutionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.configuration_template_id")
    private Long configurationTemplateId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.indicators_json")
    private String indicatorsJson;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.other_exam_attributes_json")
    private String otherExamAttributesJson;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source Table: exam_template")
    public ExamTemplateRecord(Long id, Long institutionId, Long configurationTemplateId, String name, String description, String indicatorsJson, String otherExamAttributesJson) {
        this.id = id;
        this.institutionId = institutionId;
        this.configurationTemplateId = configurationTemplateId;
        this.name = name;
        this.description = description;
        this.indicatorsJson = indicatorsJson;
        this.otherExamAttributesJson = otherExamAttributesJson;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.institution_id")
    public Long getInstitutionId() {
        return institutionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.configuration_template_id")
    public Long getConfigurationTemplateId() {
        return configurationTemplateId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.indicators_json")
    public String getIndicatorsJson() {
        return indicatorsJson;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38+02:00", comments="Source field: exam_template.other_exam_attributes_json")
    public String getOtherExamAttributesJson() {
        return otherExamAttributesJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_template
     *
     * @mbg.generated Mon Aug 30 15:10:38 CEST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", configurationTemplateId=").append(configurationTemplateId);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", indicatorsJson=").append(indicatorsJson);
        sb.append(", otherExamAttributesJson=").append(otherExamAttributesJson);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_template
     *
     * @mbg.generated Mon Aug 30 15:10:38 CEST 2021
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ExamTemplateRecord other = (ExamTemplateRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInstitutionId() == null ? other.getInstitutionId() == null : this.getInstitutionId().equals(other.getInstitutionId()))
            && (this.getConfigurationTemplateId() == null ? other.getConfigurationTemplateId() == null : this.getConfigurationTemplateId().equals(other.getConfigurationTemplateId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIndicatorsJson() == null ? other.getIndicatorsJson() == null : this.getIndicatorsJson().equals(other.getIndicatorsJson()))
            && (this.getOtherExamAttributesJson() == null ? other.getOtherExamAttributesJson() == null : this.getOtherExamAttributesJson().equals(other.getOtherExamAttributesJson()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_template
     *
     * @mbg.generated Mon Aug 30 15:10:38 CEST 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInstitutionId() == null) ? 0 : getInstitutionId().hashCode());
        result = prime * result + ((getConfigurationTemplateId() == null) ? 0 : getConfigurationTemplateId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIndicatorsJson() == null) ? 0 : getIndicatorsJson().hashCode());
        result = prime * result + ((getOtherExamAttributesJson() == null) ? 0 : getOtherExamAttributesJson().hashCode());
        return result;
    }
}