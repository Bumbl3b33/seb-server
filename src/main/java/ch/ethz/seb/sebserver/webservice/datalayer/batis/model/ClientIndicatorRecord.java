package ch.ethz.seb.sebserver.webservice.datalayer.batis.model;

import javax.annotation.Generated;

public class ClientIndicatorRecord {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.client_connection_id")
    private Long clientConnectionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.type")
    private Integer type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.805+02:00", comments="Source field: client_indicator.value")
    private Long value;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source Table: client_indicator")
    public ClientIndicatorRecord(Long id, Long clientConnectionId, Integer type, Long value) {
        this.id = id;
        this.clientConnectionId = clientConnectionId;
        this.type = type;
        this.value = value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source Table: client_indicator")
    public ClientIndicatorRecord() {
        super();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.client_connection_id")
    public Long getClientConnectionId() {
        return clientConnectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.client_connection_id")
    public void setClientConnectionId(Long clientConnectionId) {
        this.clientConnectionId = clientConnectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.type")
    public Integer getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.804+02:00", comments="Source field: client_indicator.type")
    public void setType(Integer type) {
        this.type = type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.805+02:00", comments="Source field: client_indicator.value")
    public Long getValue() {
        return value;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-08-18T13:41:40.805+02:00", comments="Source field: client_indicator.value")
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Thu Aug 18 13:41:40 CEST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientConnectionId=").append(clientConnectionId);
        sb.append(", type=").append(type);
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Thu Aug 18 13:41:40 CEST 2022
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
        ClientIndicatorRecord other = (ClientIndicatorRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClientConnectionId() == null ? other.getClientConnectionId() == null : this.getClientConnectionId().equals(other.getClientConnectionId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table client_indicator
     *
     * @mbg.generated Thu Aug 18 13:41:40 CEST 2022
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClientConnectionId() == null) ? 0 : getClientConnectionId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        return result;
    }
}