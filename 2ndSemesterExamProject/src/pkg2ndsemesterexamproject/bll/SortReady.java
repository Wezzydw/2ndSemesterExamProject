/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.List;

/**
 *
 * @author andreas
 */
public class SortReady implements ISortStrategy
{

    @Override
    public List sort(List list)
    {
        return readySort();
    }
    
    private List readySort(){
        return null;
    }
}
