package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ExamConfigurationMapRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.238+02:00", comments="Source field: exam_configuration_map.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.238+02:00", comments="Source field: exam_configuration_map.institution_id")
    private Long institutionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.exam_id")
    private Long examId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.configuration_node_id")
    private Long configurationNodeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.user_names")
    private String userNames;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.encrypt_secret")
    private String encryptSecret;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.238+02:00", comments="Source Table: exam_configuration_map")
    public ExamConfigurationMapRecord(Long id, Long institutionId, Long examId, Long configurationNodeId, String userNames, String encryptSecret) {
        this.id = id;
        this.institutionId = institutionId;
        this.examId = examId;
        this.configurationNodeId = configurationNodeId;
        this.userNames = userNames;
        this.encryptSecret = encryptSecret;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.238+02:00", comments="Source field: exam_configuration_map.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.238+02:00", comments="Source field: exam_configuration_map.institution_id")
    public Long getInstitutionId() {
        return institutionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.exam_id")
    public Long getExamId() {
        return examId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.configuration_node_id")
    public Long getConfigurationNodeId() {
        return configurationNodeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.user_names")
    public String getUserNames() {
        return userNames;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-05-12T16:13:18.239+02:00", comments="Source field: exam_configuration_map.encrypt_secret")
    public String getEncryptSecret() {
        return encryptSecret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Thu May 12 16:13:18 CEST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", examId=").append(examId);
        sb.append(", configurationNodeId=").append(configurationNodeId);
        sb.append(", userNames=").append(userNames);
        sb.append(", encryptSecret=").append(encryptSecret);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Thu May 12 16:13:18 CEST 2022
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
        ExamConfigurationMapRecord other = (ExamConfigurationMapRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInstitutionId() == null ? other.getInstitutionId() == null : this.getInstitutionId().equals(other.getInstitutionId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getConfigurationNodeId() == null ? other.getConfigurationNodeId() == null : this.getConfigurationNodeId().equals(other.getConfigurationNodeId()))
            && (this.getUserNames() == null ? other.getUserNames() == null : this.getUserNames().equals(other.getUserNames()))
            && (this.getEncryptSecret() == null ? other.getEncryptSecret() == null : this.getEncryptSecret().equals(other.getEncryptSecret()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_configuration_map
     *
     * @mbg.generated Thu May 12 16:13:18 CEST 2022
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInstitutionId() == null) ? 0 : getInstitutionId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getConfigurationNodeId() == null) ? 0 : getConfigurationNodeId().hashCode());
        result = prime * result + ((getUserNames() == null) ? 0 : getUserNames().hashCode());
        result = prime * result + ((getEncryptSecret() == null) ? 0 : getEncryptSecret().hashCode());
        return result;
    }
}