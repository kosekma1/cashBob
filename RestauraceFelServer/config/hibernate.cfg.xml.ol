<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/db_rest_fel?characterEncoding=UTF-8</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password"/>
    <property name="hibernate.connection.autocommit">true</property>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.transaction.factory_class">org.hibernate.transaction.JDBCTransactionFactory</property>
    <property name="hibernate.current_session_context_class">thread</property>
    <property name="cache.provider_class">org.hibernate.cache.NoCacheProvider</property>
    <property name="hibernate.show_sql">false</property>
    <property name="hibernate.hbm2ddl.auto">update</property>
    <mapping resource="hibernate/Income.hbm.xml"/>
    <mapping resource="hibernate/MenuMenuItem.hbm.xml"/>
    <mapping resource="hibernate/MaterialType.hbm.xml"/>
    <mapping resource="hibernate/Account.hbm.xml"/>
    <mapping resource="hibernate/Table.hbm.xml"/>
    <mapping resource="hibernate/UserRole.hbm.xml"/>
    <mapping resource="hibernate/Discount.hbm.xml"/>
    <mapping resource="hibernate/User.hbm.xml"/>
    <mapping resource="hibernate/Role.hbm.xml"/>
    <mapping resource="hibernate/UnitType.hbm.xml"/>
    <mapping resource="hibernate/Depreciation.hbm.xml"/>
    <mapping resource="hibernate/Order.hbm.xml"/>
    <mapping resource="hibernate/DiscountType.hbm.xml"/>
    <mapping resource="hibernate/UsedMaterial.hbm.xml"/>
    <mapping resource="hibernate/MenuItem.hbm.xml"/>
    <mapping resource="hibernate/Material.hbm.xml"/>
    <mapping resource="hibernate/Menu.hbm.xml"/>
    <mapping resource="hibernate/Expenditure.hbm.xml"/>
    <mapping resource="hibernate/OrderMenuItem.hbm.xml"/>
    <mapping resource="hibernate/ReasonType.hbm.xml"/>
    <mapping resource="hibernate/AccountStatusType.hbm.xml"/>
    <mapping resource="hibernate/AccountCategory.hbm.xml"/>
    <mapping resource="hibernate/MenuItemType.hbm.xml"/>
    <mapping resource="hibernate/Uzaverka.hbm.xml"/>
    <mapping resource="hibernate/Kontrola.hbm.xml"/>
    <mapping resource="hibernate/RoleDiscountType.hbm.xml"/>
    <mapping resource="hibernate/UserDiscountType.hbm.xml"/>
    <mapping resource="hibernate/Right.hbm.xml"/>
    <mapping resource="hibernate/RoleRight.hbm.xml"/>
  </session-factory>
</hibernate-configuration>
