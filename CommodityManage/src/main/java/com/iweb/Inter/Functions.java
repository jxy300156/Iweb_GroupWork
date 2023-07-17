package com.iweb.Inter;

import com.iweb.pojo.Order;
import com.iweb.pojo.Product;

import java.util.HashMap;
import java.util.List;

/**
 * @author jxy
 * @date
 */
public interface Functions {

    /**提供选项，让用户选择按照销量或者评价数或者系统推荐的方式定义比较器
     * 对商品数据进行排序之后查询并分页显示，选购商品后，将商品添加到购物车中
     * @param compareOption 作为对商品进行排序的选择传入
     * @return 返回一个一Integer类型的购物车编号作为key，Product类型的集合作为购物车value的hashmap
     */
    HashMap<Integer,List<Product>> commodityChoose(String compareOption);

    /**输入一个购物车的编号id和一个选项对购物车进行增删改操作
     * @param cartId 作为需要被进行操作的购物车的id传入
     * @param choice 作为操作的选项传入
     */
    void cartOperations(Integer cartId, int choice);

    /**输入一个购物车的编号，对对应订单进行结算，结算之后返回一个订单数据
     * @param cid 作为被选中的购物车编号传入
     * @return 返回一个Order类型的订单
     */
    HashMap<Integer,Order> cartSettlement(Integer cid);

    /** 实现输入订单编号对订单信息进行查看，详细显示订单的各项数据
     *  如果未找到数据则输出“订单信息不存在”
     * @param orderId 作为查找订单数据的索引传入
     */
    void viewOrder(int orderId);

    /** 实现对收获地址的管理，通过输入选项，按选项来对地址增删改
     * @param choice 作为选项的参数传入，用来判断接下来进行的操作
     */
    void manageAddress(int choice);

    /** 实现对账户中的余额进行充值，使用SQL语句对用户信息中的money字段做修改
     * @param recharge 作为修改的资金数值传入
     */
    void investMoney(int recharge);

    /** 使用BIO和socket编程 实现与智能客服通信 方法实现中选择调用两个方法
     * 一个实现服务器端的ServerSocket通信，另一个实现客户端的Socket通信
     * @param host 作为客户端通信时的服务器地址
     * @param port 作为客户端与服务器端通信的端口号
     */
    void customerService(String host,int port);
}
