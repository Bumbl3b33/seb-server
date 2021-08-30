package ch.ethz.seb.sebserver.webservice.datalayer.batis.mapper;

import static ch.ethz.seb.sebserver.webservice.datalayer.batis.mapper.ExamTemplateRecordDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import ch.ethz.seb.sebserver.webservice.datalayer.batis.model.ExamTemplateRecord;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

@Mapper
public interface ExamTemplateRecordMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<ExamTemplateRecord> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="institution_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="configuration_template_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="indicators_json", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="other_exam_attributes_json", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    ExamTemplateRecord selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="institution_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="configuration_template_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="indicators_json", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="other_exam_attributes_json", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<ExamTemplateRecord> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(examTemplateRecord);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, examTemplateRecord);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default int deleteByPrimaryKey(Long id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, examTemplateRecord)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default int insert(ExamTemplateRecord record) {
        return insert(SqlBuilder.insert(record)
                .into(examTemplateRecord)
                .map(institutionId).toProperty("institutionId")
                .map(configurationTemplateId).toProperty("configurationTemplateId")
                .map(name).toProperty("name")
                .map(description).toProperty("description")
                .map(indicatorsJson).toProperty("indicatorsJson")
                .map(otherExamAttributesJson).toProperty("otherExamAttributesJson")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default int insertSelective(ExamTemplateRecord record) {
        return insert(SqlBuilder.insert(record)
                .into(examTemplateRecord)
                .map(institutionId).toPropertyWhenPresent("institutionId", record::getInstitutionId)
                .map(configurationTemplateId).toPropertyWhenPresent("configurationTemplateId", record::getConfigurationTemplateId)
                .map(name).toPropertyWhenPresent("name", record::getName)
                .map(description).toPropertyWhenPresent("description", record::getDescription)
                .map(indicatorsJson).toPropertyWhenPresent("indicatorsJson", record::getIndicatorsJson)
                .map(otherExamAttributesJson).toPropertyWhenPresent("otherExamAttributesJson", record::getOtherExamAttributesJson)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<ExamTemplateRecord>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, institutionId, configurationTemplateId, name, description, indicatorsJson, otherExamAttributesJson)
                .from(examTemplateRecord);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<ExamTemplateRecord>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, institutionId, configurationTemplateId, name, description, indicatorsJson, otherExamAttributesJson)
                .from(examTemplateRecord);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default ExamTemplateRecord selectByPrimaryKey(Long id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, institutionId, configurationTemplateId, name, description, indicatorsJson, otherExamAttributesJson)
                .from(examTemplateRecord)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(ExamTemplateRecord record) {
        return UpdateDSL.updateWithMapper(this::update, examTemplateRecord)
                .set(institutionId).equalTo(record::getInstitutionId)
                .set(configurationTemplateId).equalTo(record::getConfigurationTemplateId)
                .set(name).equalTo(record::getName)
                .set(description).equalTo(record::getDescription)
                .set(indicatorsJson).equalTo(record::getIndicatorsJson)
                .set(otherExamAttributesJson).equalTo(record::getOtherExamAttributesJson);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(ExamTemplateRecord record) {
        return UpdateDSL.updateWithMapper(this::update, examTemplateRecord)
                .set(institutionId).equalToWhenPresent(record::getInstitutionId)
                .set(configurationTemplateId).equalToWhenPresent(record::getConfigurationTemplateId)
                .set(name).equalToWhenPresent(record::getName)
                .set(description).equalToWhenPresent(record::getDescription)
                .set(indicatorsJson).equalToWhenPresent(record::getIndicatorsJson)
                .set(otherExamAttributesJson).equalToWhenPresent(record::getOtherExamAttributesJson);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default int updateByPrimaryKey(ExamTemplateRecord record) {
        return UpdateDSL.updateWithMapper(this::update, examTemplateRecord)
                .set(institutionId).equalTo(record::getInstitutionId)
                .set(configurationTemplateId).equalTo(record::getConfigurationTemplateId)
                .set(name).equalTo(record::getName)
                .set(description).equalTo(record::getDescription)
                .set(indicatorsJson).equalTo(record::getIndicatorsJson)
                .set(otherExamAttributesJson).equalTo(record::getOtherExamAttributesJson)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-08-30T15:10:38.001+02:00", comments="Source Table: exam_template")
    default int updateByPrimaryKeySelective(ExamTemplateRecord record) {
        return UpdateDSL.updateWithMapper(this::update, examTemplateRecord)
                .set(institutionId).equalToWhenPresent(record::getInstitutionId)
                .set(configurationTemplateId).equalToWhenPresent(record::getConfigurationTemplateId)
                .set(name).equalToWhenPresent(record::getName)
                .set(description).equalToWhenPresent(record::getDescription)
                .set(indicatorsJson).equalToWhenPresent(record::getIndicatorsJson)
                .set(otherExamAttributesJson).equalToWhenPresent(record::getOtherExamAttributesJson)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator",comments="Source Table: exam")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ConstructorArgs({@Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true)})
    List<Long> selectIds(SelectStatementProvider select);

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Long>>> selectIdsByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectIds, id)
                        .from(examTemplateRecord);
    }
}