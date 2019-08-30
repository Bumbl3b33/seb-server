package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ClientConnectionRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.institution_id")
    private Long institutionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.exam_id")
    private Long examId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.connection_token")
    private String connectionToken;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.exam_user_session_identifer")
    private String examUserSessionIdentifer;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.client_address")
    private String clientAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.virtual_client_address")
    private String virtualClientAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.creation_time")
    private Long creationTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source Table: client_connection")
    public ClientConnectionRecord(Long id, Long institutionId, Long examId, String status, String connectionToken, String examUserSessionIdentifer, String clientAddress, String virtualClientAddress, Long creationTime) {
        this.id = id;
        this.institutionId = institutionId;
        this.examId = examId;
        this.status = status;
        this.connectionToken = connectionToken;
        this.examUserSessionIdentifer = examUserSessionIdentifer;
        this.clientAddress = clientAddress;
        this.virtualClientAddress = virtualClientAddress;
        this.creationTime = creationTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.institution_id")
    public Long getInstitutionId() {
        return institutionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.733+02:00", comments="Source field: client_connection.exam_id")
    public Long getExamId() {
        return examId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.connection_token")
    public String getConnectionToken() {
        return connectionToken;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.exam_user_session_identifer")
    public String getExamUserSessionIdentifer() {
        return examUserSessionIdentifer;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.client_address")
    public String getClientAddress() {
        return clientAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.virtual_client_address")
    public String getVirtualClientAddress() {
        return virtualClientAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2019-08-30T11:23:17.734+02:00", comments="Source field: client_connection.creation_time")
    public Long getCreationTime() {
        return creationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
     *
     * @mbg.generated Fri Aug 30 11:23:17 CEST 2019
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
        sb.append(", status=").append(status);
        sb.append(", connectionToken=").append(connectionToken);
        sb.append(", examUserSessionIdentifer=").append(examUserSessionIdentifer);
        sb.append(", clientAddress=").append(clientAddress);
        sb.append(", virtualClientAddress=").append(virtualClientAddress);
        sb.append(", creationTime=").append(creationTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
     *
     * @mbg.generated Fri Aug 30 11:23:17 CEST 2019
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
        ClientConnectionRecord other = (ClientConnectionRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInstitutionId() == null ? other.getInstitutionId() == null : this.getInstitutionId().equals(other.getInstitutionId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getConnectionToken() == null ? other.getConnectionToken() == null : this.getConnectionToken().equals(other.getConnectionToken()))
            && (this.getExamUserSessionIdentifer() == null ? other.getExamUserSessionIdentifer() == null : this.getExamUserSessionIdentifer().equals(other.getExamUserSessionIdentifer()))
            && (this.getClientAddress() == null ? other.getClientAddress() == null : this.getClientAddress().equals(other.getClientAddress()))
            && (this.getVirtualClientAddress() == null ? other.getVirtualClientAddress() == null : this.getVirtualClientAddress().equals(other.getVirtualClientAddress()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_connection
     *
     * @mbg.generated Fri Aug 30 11:23:17 CEST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInstitutionId() == null) ? 0 : getInstitutionId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getConnectionToken() == null) ? 0 : getConnectionToken().hashCode());
        result = prime * result + ((getExamUserSessionIdentifer() == null) ? 0 : getExamUserSessionIdentifer().hashCode());
        result = prime * result + ((getClientAddress() == null) ? 0 : getClientAddress().hashCode());
        result = prime * result + ((getVirtualClientAddress() == null) ? 0 : getVirtualClientAddress().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        return result;
    }
}