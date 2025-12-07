package cz.matfyz.server.example.adaptation;

import cz.matfyz.server.mapping.MappingEntity;
import cz.matfyz.server.mapping.MappingService;
import cz.matfyz.server.querying.Query;
import cz.matfyz.server.querying.QueryService;
import cz.matfyz.server.utils.Configuration.SetupProperties;
import cz.matfyz.server.utils.entity.Id;
import cz.matfyz.server.category.SchemaCategoryEntity;
import cz.matfyz.server.category.SchemaCategoryService;
import cz.matfyz.server.category.SchemaCategoryService.SchemaEvolutionInit;
import cz.matfyz.server.datasource.DatasourceEntity;
import cz.matfyz.server.datasource.DatasourceService;
import cz.matfyz.server.example.common.DatasourceBuilder;
import cz.matfyz.server.example.common.MappingEntityBuilder;
import cz.matfyz.server.example.common.QueryBuilder;
import cz.matfyz.tests.example.adaptation.PostgreSQL;
import cz.matfyz.tests.example.adaptation.Neo4j;
import cz.matfyz.tests.example.adaptation.Schema;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("adaptationExampleSetup")
public class ExampleSetup {

    public SchemaCategoryEntity setup() {
        final SchemaCategoryEntity schema = createSchemaCategory();
        final List<DatasourceEntity> datasources = createDatasources();
        createMappings(datasources, schema);

        return schema;
    }

    @Autowired
    private SchemaCategoryService schemaService;

    private SchemaCategoryEntity createSchemaCategory() {
        final SchemaCategoryEntity schemaEntity = schemaService.create(Schema.schemaLabel);

        final SchemaEvolutionInit schemaUpdate = SchemaSetup.createNewUpdate(schemaEntity);

        createQueries(schemaEntity.id());

        return schemaService.update(schemaEntity.id(), schemaUpdate);
    }

    @Autowired
    private SetupProperties properties;

    @Autowired
    private DatasourceService datasourceService;

    private List<DatasourceEntity> createDatasources() {
        final var builder = new DatasourceBuilder(properties, properties.adaptationDatabase());

        return datasourceService.createIfNotExists(List.of(
            builder.createPostgreSQL("PostgreSQL"),
            builder.createMongoDB("MongoDB"),
            builder.createNeo4j("Neo4j")
        ));
    }

    @Autowired
    private MappingService mappingService;

    private List<MappingEntity> createMappings(List<DatasourceEntity> datasources, SchemaCategoryEntity schemaEntity) {
        return new MappingEntityBuilder(datasources, schemaEntity)
            .add(0, PostgreSQL::orders)
            .add(0, PostgreSQL::lineItem)
            .add(2, Neo4j::supplier)
            .add(2, Neo4j::part)
            .add(2, Neo4j::partSupp)
            .add(2, Neo4j::suppliedBy)
            .add(2, Neo4j::isForPart)
            .build(mappingService::create);
    }

    @Autowired
    private QueryService queryService;

    private List<Query> createQueries(Id categoryId) {
        return new QueryBuilder(categoryId)
            .add("LineItem summary", mockQuery)
            .add("Region revenue", mockQuery)
            .add("Discounted revenue", mockQuery)
            .add("Top customers", mockQuery)
            .add("Large-quantity line items", mockQuery)
            .add("Top high-balance customers", mockQuery)
            .build(queryService::create);
    }

    // FIXME replace with real queries
    final private String mockQuery = """
        SELECT {
            ?orders key ?orderkey .
        }
        WHERE {
            ?orders 70 ?orderkey .
        }
        """;

}
