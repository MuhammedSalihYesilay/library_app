package com.library.library_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "room_number")
    private int roomNumber;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "room_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RoomTypeEntity roomType;

   @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
   @ToString.Exclude
   @EqualsAndHashCode.Exclude
   private Set<ReservationEntity> reservations = new HashSet<>();

}