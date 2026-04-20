//package com.laptopshop.models.refunds;
//
//
//import com.laptopshop.models.orders.OrderItem;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Table(
//        name = "return_request_items",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uq_return_line",
//                        columnNames = {"return_id", "order_item_id"}
//                )
//        }
//)
//@Entity
//@Getter
//@Setter
//public class ReturnRequestItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "return_id", nullable = false)
//    private ReturnRequest returnRequest;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_item_id", nullable = false)
//    private OrderItem orderItem;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @Column(name = "condition_note", length = 500)
//    private String conditionNote;
//}
