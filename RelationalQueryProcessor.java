//Israel Alcantara

package edu.njit.cs114;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/27/2023
 */

public class RelationalQueryProcessor
{
    private static Random random = new Random();

    // get all the rows of the table t
    private static RelationalTable.DataRow[] getRows(RelationalTable t)
    {
        RelationalTable.DataRow[] rows = new RelationalTable.DataRow[t.size()];

        /**
         * Complete code for the lab
         */

        Iterator<RelationalTable.DataRow> rowIterator = t.getRowIterator();
        int index = 0;

        while (rowIterator.hasNext())
        {
            rows[index] = rowIterator.next();
            index++;
        }
        return rows;
    }

    private String colName(String alias, String col)
    {
        int idx = col.lastIndexOf(".");
        if (idx >= 0)
        {
            return alias + "." + col.substring(idx + 1);
        }
        else
        {
            return alias + "." + col;
        }
    }

    /**
     * Create an empty table for join
     *
     * @param name   table name
     * @param table1 left table
     * @param table2 right table
     * @param alias1 alias to use for columns of left table
     * @param alias2 alias to use for columns of right table
     * @return
     */
    private RelationalTable createJoinResultTable(String name,
                                                  RelationalTable table1,
                                                  RelationalTable table2,
                                                  String alias1,
                                                  String alias2)
    {
        List<String> columns = new ArrayList<>();
        for (String col : table1.columns())
        {
            columns.add(colName(alias1, col));
        }
        for (String col : table2.columns())
        {
            columns.add(colName(alias2, col));
        }
        return new RelationalTable(name, columns.toArray(new String[0]));
    }

    /**
     * Creates a data row by combining columns for table1 and table2
     * Used by Join operation
     *
     * @param table1      left table
     * @param table2      right table
     * @param alias1      alias to use for columns of left table
     * @param alias2      alias to use for columns of right table
     * @param mergedTable join table
     * @param row1        row of left table
     * @param row2        row of right table
     * @return
     */
    private RelationalTable.DataRow mergeRows
    (
            RelationalTable table1,
            RelationalTable table2,
            String alias1,
            String alias2,
            RelationalTable mergedTable,
            RelationalTable.DataRow row1,
            RelationalTable.DataRow row2)
    {
        RelationalTable.DataRow mergedRow = mergedTable.createEmptyRow();
        for (String col : table1.columns())
        {
            mergedRow.setValue(colName(alias1, col), row1.getValue(col));
        }
        for (String col : table2.columns())
        {
            mergedRow.setValue(colName(alias2, col), row2.getValue(col));
        }
        return mergedRow;
    }

    /**
     * Joins table1 and table2 using the nested loop join columns specified in joinColumns
     * NOTE : DO not sort rows !!!
     *
     * @param table1          left table (can have many rows with the same value in joinColumns[0] column
     * @param table2          right table (assumed to have unique values in joinColumns[1] column
     * @param alias1          alias to use for left table columns in the result
     * @param alias2          alias to use for right table columns in the result
     * @param joinColumns     joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName name of result table
     * @return a relational table
     */
    public RelationalTable nestedLoopJoin(RelationalTable table1, RelationalTable table2,
                                          String alias1, String alias2,
                                          String[] joinColumns, String resultTableName)
    {
        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);

        RelationalTable.RowComparator comp =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        Iterator<RelationalTable.DataRow> iter1 = table1.getRowIterator();
        while (iter1.hasNext())
        {

            /**
             * Complete code for homework
             * Get row for table 1
             * Get iterator for rows of table2 and loop through the rows of table 2
             */

            RelationalTable.DataRow row1 = iter1.next();
            Iterator<RelationalTable.DataRow> iter2 = table2.getRowIterator();
            while (iter2.hasNext())
            {
                RelationalTable.DataRow row2 = iter2.next();
                int compResult = comp.compare(row1, row2);
                if (compResult == 0)
                {
                    joinTable.addRow(mergeRows(table1, table2, alias1, alias2, joinTable, row1, row2));
                }
            }
        }
        return joinTable;
    }

    public RelationalTable nestedLoopJoin(RelationalTable table1, RelationalTable table2,
                                          String[] joinColumns, String resultTableName)
    {
        return nestedLoopJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }

    /**
     * Joins using index join table1 and table2 using the join columns specified in joinColumns
     *
     * @param table1          left table (can have many rows with the same value in joinColumns[0] column
     * @param table2          right table (assumed to have unique values in joinColumns[1] column
     * @param alias1          alias to use for left table columns in the result
     * @param alias2          alias to use for right table columns in the result
     * @param joinColumns     joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName name of result table
     * @return a relational table
     */
    public RelationalTable indexJoin(RelationalTable table1, RelationalTable table2,
                                     String alias1, String alias2,
                                     String []  joinColumns, String resultTableName)
    {
        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);

        RelationalTable.RowComparator comp =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        Iterator<RelationalTable.DataRow> iter1 = table1.getIndexRowIterator(joinColumns[0]);
        Iterator<RelationalTable.DataRow> iter2 = table2.getIndexRowIterator(joinColumns[1]);

        /**
         * Complete code for the homework assignment
         * Note rows will be retrieved in sorted order (do not sort!!)
         */

        RelationalTable.DataRow row1;
        RelationalTable.DataRow row2 = null;
        int num = -1;

        while (iter1.hasNext())
        {
            row1 = iter1.next();

            while (iter2.hasNext() && num < 0)
            {
                row2 = iter2.next();
                num = comp.compare(row1, row2);
            }


            if (num == 0)
            {
                joinTable.addRow(mergeRows(table1, table2, alias1, alias2, joinTable, row1, row2));
            }
        }
        return joinTable;
    }

    public RelationalTable indexJoin(RelationalTable table1, RelationalTable table2,
                                     String []  joinColumns, String resultTableName)
    {
        return indexJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }


    /**
     * Joins (using merge-join) table1 and table2 using the join columns specified in joinColumns
     * @param table1 left table (can have many rows with the same value in joinColumns[0] column
     * @param table2 right table (assumed to have unique values in joinColumns[1] column
     * @param alias1 alias to use for left table columns in the result
     * @param alias2 alias to use for right table columns in the result
     * @param joinColumns joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName
     * @return a relational table
     */
    public RelationalTable mergeJoin(RelationalTable table1, RelationalTable table2,
                                     String alias1, String alias2,
                                     String []  joinColumns, String resultTableName)
    {
        RelationalTable.RowComparator comp1 =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[0]);
        RelationalTable.RowComparator comp2 =
                new RelationalTable.RowComparator(joinColumns[1], joinColumns[1]);
        // sort left table rows according to column joinColumn[0]
        RelationalTable.DataRow[] rows1 = getRows(table1);

        Arrays.sort(rows1, comp1);

        // sort right table rows according to column joinColumn[1]
        RelationalTable.DataRow[] rows2 = getRows(table2);
        Arrays.sort(rows2, comp2);


        //arrays.sort(array to be sorted (r1), comparitor we need to sort (r2))
        // to merge, use mergeRows


        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);
        // merge
        int idx1 = 0;
        int idx2 = 0;
        RelationalTable.RowComparator comp3 =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        /**
         * To be completed for the lab
         */


        while (idx1 < rows1.length && idx2 < rows2.length)
        {
            int compareResult = comp3.compare(rows1[idx1], rows2[idx2]);
            if (compareResult == 0)
            {
                RelationalTable.DataRow mergedRow = mergeRows(table1, table2, alias1, alias2, joinTable, rows1[idx1], rows2[idx2]);
                joinTable.addRow(mergedRow);
                idx1++;

            }
            else if (compareResult < 0) {idx1++;}
            else {idx2++;}
        }

        return joinTable;
    }


    public RelationalTable mergeJoin(RelationalTable table1, RelationalTable table2,
                                    String []  joinColumns, String resultTableName)
    {
        return mergeJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }

    private static void swap(RelationalTable.DataRow [] arr, int k1, int k2)
    {
        RelationalTable.DataRow  temp = arr[k1];
        arr[k1] = arr[k2];
        arr[k2] = temp;
    }

    /**
     * Return the index pivIndex of the pivot element after partitioning sub array
     * After partitioning left sub array will be in dataArr[fromIndex..pivIndex-1]
     *  right sub array will be in dataArr[pivIndex...toIndex-1]
     * @param dataArr
     * @param comp
     * @param fromIndex starting index of sub array
     * @param toIndex ending index (not including) of sub array i.e sub array is given
     *                by dataArr[fromIndex..toIndex-1]
     * @param pivotElementIdx index of pivot element before partitioning
     * @return
     */

    public static int partition(RelationalTable.DataRow [] dataArr,
                                RelationalTable.RowComparator comp,
                                int fromIndex, int toIndex,
                                int pivotElementIdx)
    {
        /**
         * Complete the code here for homework
         */
        swap(dataArr, fromIndex, pivotElementIdx);
        pivotElementIdx = fromIndex;
        int left = fromIndex + 1;
        int right = toIndex - 1;

        while (left <= right)
        {
            if (comp.compare(dataArr[left],dataArr[pivotElementIdx])<=0)
            {
                left++;

            }
            else if (comp.compare(dataArr[pivotElementIdx],dataArr[right])<0)
            {
                right--;
            }
            else
            {
                swap(dataArr, left ,right);
            }
        }
        swap(dataArr, fromIndex, right);
        return right;
    }

    /**
     * Find k-th smallest element of the subarray dataArr[fromIndex..toIndex-1] using a random pivot
     * @param dataArr
     * @param comp
     * @param fromIndex
     * @param toIndex
     * @param k
     * @return
     */
    private static RelationalTable.DataRow kthSmallestRandomPivot(RelationalTable.DataRow [] dataArr,
                                                                  RelationalTable.RowComparator comp,
                                                                  int fromIndex, int toIndex, int k)
    {

        if (toIndex - fromIndex <= 1)
        {
            return dataArr[fromIndex];
        }
        /**

         Complete the code here for homework*/
        int pivotPos = partition(dataArr, comp, fromIndex, toIndex, random.nextInt(toIndex - fromIndex) + fromIndex);

        if (k <= pivotPos - fromIndex)
        {
            return kthSmallestRandomPivot(dataArr, comp, fromIndex, pivotPos, k);
        }
        else if (k == pivotPos - fromIndex + 1)
        {
            return dataArr[pivotPos];
        }
        else
        {
            return kthSmallestRandomPivot(dataArr, comp, pivotPos + 1, toIndex, k - (pivotPos - fromIndex + 1));
        }
    }

    public static RelationalTable.DataRow kthSmallest(RelationalTable table, String column, int k)
    {
        RelationalTable.DataRow[] rows = getRows(table);
        RelationalTable.RowComparator comp =
                new RelationalTable.RowComparator(column, column);
        return kthSmallestRandomPivot(rows, comp, 0, rows.length, k);
    }

 
    private static void insertEmployeeRow(RelationalTable t,
                                          Object [] columnVals)
    {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("employeeId", columnVals[idx++]);
        row.setValue("firstName", columnVals[idx++]);
        row.setValue("lastName", columnVals[idx++]);
        row.setValue("deptId", columnVals[idx++]);
        row.setValue("annualSalary", columnVals[idx++]);
        t.addRow(row);
    }

    private static void insertDeptRow(RelationalTable t,
                                      Object [] columnVals)
    {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("deptId", columnVals[idx++]);
        row.setValue("deptName", columnVals[idx++]);
        row.setValue("managerId", columnVals[idx++]);
        t.addRow(row);
    }

    public static void performanceCheck(int nEmployees, int nDepartments)
    {
        RelationalTable empTable = new RelationalTable("Employee",
                new String[]{"employeeId", "firstName", "lastName",
                        "annualSalary", "deptId"});
        empTable.addIndex("employeeId");
        empTable.addIndex("deptId");
        Random random = new Random();
        for (int i = 1; i <= nEmployees - nDepartments; i++)
        {
            int deptId = 1 + random.nextInt(nDepartments);
            insertEmployeeRow(empTable, new Object[]{
                    i, "Lowly", "Worker" + i, deptId, 30000});
        }
        RelationalTable deptTable = new RelationalTable("Department",
                new String[]{"deptId", "deptName", "managerId"});
        deptTable.addIndex("deptId");
        deptTable.addIndex("managerId");
        for (int i = 1; i <= nDepartments; i++)
        {
            insertEmployeeRow(empTable, new Object[]{
                    i, "Boss", "Worker" + i, i, 150000});
            insertDeptRow(deptTable, new Object[]{i, "Dept" + i, nEmployees - nDepartments + i});
        }
        RelationalQueryProcessor proc = new RelationalQueryProcessor();

        RelationalTable.RowComparator.reset();
        System.out.println("nComps="+ RelationalTable.RowComparator.nComps());
        // join employee table and dept table on the deptId column using index join
        RelationalTable t3 = proc.indexJoin(empTable, deptTable, "t1", "t2",
                new String[]{"deptId", "deptId"}, "Emp-Dept");
        System.out.println("Size of Emp-Dept with index join = " + t3.size());
        System.out.println("Number of comparisons made with index join = " + RelationalTable.RowComparator.nComps());

        RelationalTable.RowComparator.reset();
        System.out.println("nComps="+ RelationalTable.RowComparator.nComps());
        // join employee table and dept table on the deptId column using sort-merge
        t3 = proc.mergeJoin(empTable, deptTable, "t1", "t2",
                new String[]{"deptId", "deptId"}, "Emp-Dept");
        System.out.println("Size of Emp-Dept with sort-merge join = " + t3.size());
        System.out.println("Number of comparisons made with sort-merge join = " + RelationalTable.RowComparator.nComps());

        RelationalTable.RowComparator.reset();
        System.out.println("nComps="+ RelationalTable.RowComparator.nComps());
        // join employee table and dept table on the deptId column using nested-loop
        t3 = proc.nestedLoopJoin(empTable, deptTable, "t1", "t2",
                new String[]{"deptId", "deptId"}, "Emp-Dept");
        System.out.println("Size of Emp-Dept with nested loop join = " + t3.size());
        System.out.println("Number of comparisons made with nested loop join = " + RelationalTable.RowComparator.nComps());
    }

    public static void homeworkTest() {
        RelationalTable empTable = new RelationalTable("Employee",
                new String [] {"employeeId", "firstName", "lastName",
                        "annualSalary", "deptId"});
        empTable.addIndex("deptId");
        insertEmployeeRow(empTable, new Object [] {
                "5000","Lowly","Worker1", 101, 30000});
        insertEmployeeRow(empTable, new Object [] {
                "5001","Lowly","Worker2", 102, 50000});
        insertEmployeeRow(empTable, new Object [] {
                "5002","Lowly","Worker3", 102, 60000});
        insertEmployeeRow(empTable, new Object [] {
                "5003","Boss1","Worker4", 102, 150000});
        insertEmployeeRow(empTable, new Object [] {
                "5004","Boss2","Worker5", 101, 200000});
        empTable.print();
        RelationalTable deptTable = new RelationalTable("Department",
                new String [] {"deptId", "deptName", "managerId"});
        deptTable.addIndex("deptId");

        insertDeptRow(deptTable, new Object [] {101, "Sales", "5004"});
        insertDeptRow(deptTable, new Object [] {102, "Manufacturing", "5003"});
        deptTable.print();

        RelationalQueryProcessor proc = new RelationalQueryProcessor();

        // join employee table and dept table on the deptId column
        RelationalTable t3 = proc.mergeJoin(empTable,deptTable, "t1", "t2",
                new String[] { "deptId", "deptId"}, "Emp-Dept(sort-merge)");
        t3.print();

        t3 = proc.indexJoin(empTable,deptTable, "t1", "t2",
                new String[] { "deptId", "deptId"}, "Emp-Dept(index-join)");
        t3.print();

        t3 = proc.nestedLoopJoin(empTable,deptTable, "t1", "t2",
                new String[] { "deptId", "deptId"}, "Emp-Dept(nested_loop)");
        t3.print();

        RelationalTable empTable1 = new RelationalTable("Employee",
                new String [] {"employeeId", "firstName", "lastName",
                        "annualSalary", "deptId"});
        for (int i = 1; i <= 100000; i++) {
            int salary = 50000 + random.nextInt(150000);
            insertEmployeeRow(empTable1, new Object[]{
                    i, "Lowly", "Worker" + i, 1, salary});
        }
        RelationalTable.RowComparator.reset();
        RelationalTable.DataRow selectedRow = kthSmallest(empTable1, "annualSalary", 50000);
        System.out.println("Employee with median salary = " + selectedRow.toString());
        System.out.println("Number of comparisons made by using selection algorithm = " +
                RelationalTable.RowComparator.nComps());
        RelationalTable.RowComparator.reset();
        RelationalTable.DataRow [] empDept1Rows = getRows(empTable1);
        Arrays.sort(empDept1Rows, new RelationalTable.RowComparator("annualSalary", "annualSalary"));
        System.out.println("Employee with median salary = " + empDept1Rows[50000-1].toString());
        System.out.println("Number of comparisons made by using sorting = " +
                RelationalTable.RowComparator.nComps());
    }

    public static void labTest() {
        RelationalTable empTable = new RelationalTable("Employee",
                new String [] {"employeeId", "firstName", "lastName",
                        "annualSalary", "deptId"});
        insertEmployeeRow(empTable, new Object [] {
                "5000","Lowly","Worker1", 101, 30000});
        insertEmployeeRow(empTable, new Object [] {
                "5001","Lowly","Worker2", 102, 50000});
        insertEmployeeRow(empTable, new Object [] {
                "5002","Lowly","Worker3", 102, 60000});
        insertEmployeeRow(empTable, new Object [] {
                "5003","Boss1","Worker4", 102, 150000});
        insertEmployeeRow(empTable, new Object [] {
                "5004","Boss2","Worker5", 101, 200000});
        empTable.print();
        RelationalTable deptTable = new RelationalTable("Department",
                new String [] {"deptId", "deptName", "managerId"}) ;

        insertDeptRow(deptTable, new Object [] {101, "Sales", "5004"});
        insertDeptRow(deptTable, new Object [] {102, "Manufacturing", "5003"});
        deptTable.print();

        RelationalQueryProcessor proc = new RelationalQueryProcessor();

        // join employee table and dept table on the deptId column
        RelationalTable t3 = proc.mergeJoin(empTable,deptTable, "t1", "t2",
                new String[] { "deptId", "deptId"}, "Emp-Dept");
        t3.print();
        RelationalTable t4 = t3.project("Emp-Dept1", new String [] {"t1.employeeId",
                "t1.firstName", "t1.lastName", "t1.deptId", "t2.deptName", "t2.managerId"} );
        t4.print();

        // join t4 and employee table to get manager information also
        RelationalTable t5 = proc.mergeJoin(t4, empTable, "emp", "mgr",
                new String[] { "t2.managerId", "employeeId"}, "t5")
                .project("Emp-Mgr", new String []{"emp.employeeId",
                        "emp.firstName", "emp.lastName", "emp.deptId", "emp.deptName",
                        "mgr.firstName", "mgr.lastName"});
        t5.print();
    }

    public static void main(String [] args) {
         labTest();
         homeworkTest();
        performanceCheck(2000, 100);
        performanceCheck(5000, 100);
        performanceCheck(10000, 100);
        performanceCheck(15000, 100);
        performanceCheck(30000, 100);
        performanceCheck(50000, 100);
        performanceCheck(100000, 100);
        performanceCheck(200000, 100);
        performanceCheck(500000, 100);
    }

}
