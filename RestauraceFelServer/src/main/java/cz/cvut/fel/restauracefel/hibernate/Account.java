package cz.cvut.fel.restauracefel.hibernate;

import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class Account extends DBEntity {

    private static final long serialVersionUID = -345056626508032692L;
    private Integer accountId;
    private String name;
    private Table table;
    private User user;
    private AccountStatusType accountStatusType;
    private AccountCategory accountCategory;
    private DiscountType discountType;
    private String note;
    private int isDeleted;

    public Account() {
    }

    public Account(String name, AccountStatusType accountStatusType, AccountCategory accountCategory, Table table, User user, DiscountType discountType, String note, int isDeleted) {
        this.name = name;
        this.accountStatusType = accountStatusType;
        this.accountCategory = accountCategory;
        this.table = table;
        this.user = user;
        this.discountType = discountType;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatusType getAccountStatusType() {
        return this.accountStatusType;
    }

    public void setAccountStatusType(AccountStatusType accountstatustype) {
        this.accountStatusType = accountstatustype;
    }

    public AccountCategory getAccountCategory() {
        return this.accountCategory;
    }

    public void setAccountCategory(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DiscountType getDiscountType() {
        return this.discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static Account findById(Integer id) {
        return (Account) findByIdNotDeleted("Account", "accountId", id, "isDeleted", 0);
    }

    public static Account findByName(String name) {
        //return (Account) findByStringNameNotDeleted("Account", "name", name, "isDeleted", 0);
        return findByNameAndAccountStatusType(name, 1);
    }

    public static Account findByNameAndAccountStatusType(String name, Integer accountStatusTypeId) {
        String query = "from Account ac where ac.name = :id1 and accountStatusType.accountStatusTypeId = :id2 and ac.isDeleted = :id3 ";
		String[] paramNames = new String[] {"id1", "id2", "id3"};
		String[] paramTypes = new String[] {"String", "Integer", "Integer"};
		Object[] paramValues = new Object[] {name, accountStatusTypeId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return (Account) res.get(0);
    }

    public static List findByTable(Integer tableId) {
        String query = "from Account ac where ac.table.tableId = :id1 and ac.isDeleted = :id2 ";
		String[] paramNames = new String[] {"id1", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {tableId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    public static List findByUser(Integer userId) {
        String query = "from Account ac where ac.user.userId = :id1 and ac.isDeleted = :id2 ";
		String[] paramNames = new String[] {"id1", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {userId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    public static List findByDiscountType(Integer discountTypeId) {
        String query = "from Account ac where ac.discountType.discountTypeId = :id1 and ac.isDeleted = :id2 ";
		String[] paramNames = new String[] {"id1", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {discountTypeId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    public static List findByAccountCategory(Integer accountCategoryId) {
        String query = "from Account ac where ac.accountCategory.accountCategoryId = :id1 and ac.isDeleted = :id2 ";
		String[] paramNames = new String[] {"id1", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {accountCategoryId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    public static List findByAccountStatusType(Integer accountStatusTypeId) {
        String query = "from Account ac where ac.accountStatusType.accountStatusTypeId = :id1 and ac.isDeleted = :id2 ";
		String[] paramNames = new String[] {"id1", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {accountStatusTypeId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    public static List findByDate(String dateFrom, String dateTo) {
        String query = "from Account ac where ac.accountId in (select o.account.accountId from Order o where o.time > :date1 and o.time < :date2 and o.isDeleted = :id2) and ac.isDeleted = :id2";
		String[] paramNames = new String[] {"date1", "date2", "id2"};
		String[] paramTypes = new String[] {"String", "String", "Integer"};
		Object[] paramValues = new Object[] {dateFrom, dateTo, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
	return res;
    }

    //vraci vsechny ucty, ktere nejsou oznaceny jako smazane
    public static List findAll() {
        return findAllNotDeleted("Account", "isDeleted", 0);
    }
}


