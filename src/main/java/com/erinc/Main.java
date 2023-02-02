package com.erinc;


import com.erinc.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    /*public static void main(String[] args) {
        Session ss = HibernateUtils.getSessionFactory().openSession();
        Transaction tt = ss.beginTransaction();
        List<String > list1 = Arrays.asList("cooking","playstation");
        List<String > list2 = Arrays.asList("treking","BBuilding");
        Map<AddressType, Address> mapAdres = new HashMap<>();
        Map<AddressType, Address> mapAdres2 = new HashMap<>();
        mapAdres.put(AddressType.BUSINESS,Address.builder().city("Eindhoven").street("Freedom").country("Nederland").build());
        mapAdres2.put(AddressType.HOME,Address.builder().city("Eindhoven").street("Home").country("Nederland").build());
        User user = User.builder()
                .name(Name.builder().name("Semiramis").surname("Tursucu").build())
                .username("Semiryan")
                .password("+31")
                .gender(EGender.MAN)
                .ilgiAlanlari(list1)
                .address(mapAdres)
                    .build();
        User user2 = User.builder()
                .name(Name.builder().name("SuMotoru").surname("Motorumsu").build())
                .username("Bensu")
                .password("12")
                .gender(EGender.WOMAN)
                .ilgiAlanlari(list2)
                .address(mapAdres2)
                .build();
        ss.save(user);
        ss.save(user2);

        tt.commit();
        ss.close();
    }*/
}