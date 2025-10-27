package com.example.ShopDt.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private float discount;
    private LocalDateTime createAt;
    private LocalDateTime expireAt;
    private int quantity;
    private int usedQuantity;
    private int status;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private Set<UserVoucher> userVouchers = new HashSet<>();
}