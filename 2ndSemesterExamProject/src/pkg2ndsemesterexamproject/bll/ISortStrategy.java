/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author andreas
 */
public interface ISortStrategy
{

    /**
     *
     * @param <error>
     * @return
     */
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName);
}
