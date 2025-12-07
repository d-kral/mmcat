package cz.matfyz.server.example.adaptation;

import cz.matfyz.server.category.SchemaCategoryEntity;
import cz.matfyz.server.category.SchemaCategoryService.SchemaEvolutionInit;
import cz.matfyz.server.example.common.SchemaBase;
import cz.matfyz.tests.example.adaptation.Schema;

class SchemaSetup extends SchemaBase {

    private SchemaSetup(SchemaCategoryEntity categoryEntity) {
        super(categoryEntity, Schema.newSchema());
    }

    static SchemaEvolutionInit createNewUpdate(SchemaCategoryEntity categoryEntity) {
        return new SchemaSetup(categoryEntity).innerCreateNewUpdate();
    }

    @Override protected void createOperations() {
        // Region
        addObjex(Schema.region, 0, 3);
        addProperty(Schema.rRegionkey, Schema.region_rRegionkey, 1, 3);
        addIds(Schema.region);

        addProperty(Schema.rName, Schema.region_rName, 1, 3);
        addProperty(Schema.rComment, Schema.region_rComment, 1, 3);

        // Nation
        addObjex(Schema.nation, 0, 1);
        addProperty(Schema.nNationkey, Schema.nation_nNationkey, -1, 2);
        addIds(Schema.nation);

        addProperty(Schema.nName, Schema.nation_nName, -1, 2);
        addProperty(Schema.nComment, Schema.nation_nComment, -1, 2);
        addMorphism(Schema.nation_region);

        // Supplier
        addObjex(Schema.supplier, 4, 1);
        addProperty(Schema.sSuppkey, Schema.supplier_sSuppkey, 5, 1);
        addIds(Schema.supplier);

        addProperty(Schema.sName, Schema.supplier_sName, 5, 1);
        addProperty(Schema.sAddress, Schema.supplier_sAddress, 5, 1);
        addProperty(Schema.sPhone, Schema.supplier_sPhone, 5, 1);
        addProperty(Schema.sAcctbal, Schema.supplier_sAcctbal, 5, 1);
        addProperty(Schema.sComment, Schema.supplier_sComment, 5, 1);
        addMorphism(Schema.supplier_nation);

        // Part
        addObjex(Schema.part, 8, -1);
        addProperty(Schema.pPartkey, Schema.part_pPartkey, 8, -2);
        addIds(Schema.part);

        addProperty(Schema.pName, Schema.part_pName, 8, -2);
        addProperty(Schema.pMfgr, Schema.part_pMfgr, 8, -2);
        addProperty(Schema.pBrand, Schema.part_pBrand, 8, -2);
        addProperty(Schema.pType, Schema.part_pType, 8, -2);
        addProperty(Schema.pSize, Schema.part_pSize, 8, -2);
        addProperty(Schema.pContainer, Schema.part_pContainer, 8, -2);
        addProperty(Schema.pComment, Schema.part_pComment, 8, -2);
        addProperty(Schema.pRetailprice, Schema.part_pRetailprice, 8, -2);

        // PartSupp
        addObjex(Schema.partSupp, 4, -1);
        addMorphism(Schema.partSupp_part);
        addMorphism(Schema.partSupp_supplier);
        addIds(Schema.partSupp);

        addProperty(Schema.psAvailqty, Schema.partSupp_psAvailqty, 4, -2);
        addProperty(Schema.psSupplycost, Schema.partSupp_psSupplycost, 4, -2);
        addProperty(Schema.psComment, Schema.partSupp_psComment, 4, -2);

        // Customer
        addObjex(Schema.customer, -4, 1);
        addProperty(Schema.cCustkey, Schema.customer_cCustkey, -4, 2);
        addIds(Schema.customer);

        addProperty(Schema.cName, Schema.customer_cName, -4, 2);
        addProperty(Schema.cAddress, Schema.customer_cAddress, -4, 2);
        addProperty(Schema.cPhone, Schema.customer_cPhone, -4, 2);
        addProperty(Schema.cAcctbal, Schema.customer_cAcctbal, -4, 2);
        addProperty(Schema.cMktsegment, Schema.customer_cMktsegment, -4, 2);
        addProperty(Schema.cComment, Schema.customer_cComment, -4, 2);
        addMorphism(Schema.customer_nation);

        // Orders
        addObjex(Schema.orders, -4, -1);
        addProperty(Schema.oOrderkey, Schema.orders_oOrderkey, -4, -2);
        addIds(Schema.orders);

        addProperty(Schema.oOrderstatus, Schema.orders_oOrderstatus, -4, -2);
        addProperty(Schema.oTotalprice, Schema.orders_oTotalprice, -4, -2);
        addProperty(Schema.oOrderdate, Schema.orders_oOrderdate, -4, -2);
        addProperty(Schema.oOrderpriority, Schema.orders_oOrderpriority, -4, -2);
        addProperty(Schema.oShippriority, Schema.orders_oShippriority, -4, -2);
        addProperty(Schema.oClerk, Schema.orders_oClerk, -4, -2);
        addProperty(Schema.oComment, Schema.orders_oComment, -4, -2);
        addMorphism(Schema.orders_customer);

        // LineItem
        addObjex(Schema.lineItem, 0, -1);
        addMorphism(Schema.lineItem_orders);
        addProperty(Schema.lLinenumber, Schema.lineItem_lLinenumber, 0, -2);
        addIds(Schema.lineItem);

        addProperty(Schema.lQuantity, Schema.lineItem_lQuantity, 0, -2);
        addProperty(Schema.lExtendedprice, Schema.lineItem_lExtendedprice, 0, -2);
        addProperty(Schema.lDiscount, Schema.lineItem_lDiscount, 0, -2);
        addProperty(Schema.lTax, Schema.lineItem_lTax, 0, -2);
        addProperty(Schema.lReturnflag, Schema.lineItem_lReturnflag, 0, -2);
        addProperty(Schema.lLinestatus, Schema.lineItem_lLinestatus, 0, -2);
        addProperty(Schema.lShipdate, Schema.lineItem_lShipdate, 0, -2);
        addProperty(Schema.lCommitdate, Schema.lineItem_lCommitdate, 0, -2);
        addProperty(Schema.lReceiptdate, Schema.lineItem_lReceiptdate, 0, -2);
        addProperty(Schema.lShipinstruct, Schema.lineItem_lShipinstruct, 0, -2);
        addProperty(Schema.lShipmode, Schema.lineItem_lShipmode, 0, -2);
        addProperty(Schema.lComment, Schema.lineItem_lComment, 0, -2);
        addMorphism(Schema.lineItem_partSupp);
    }

}
