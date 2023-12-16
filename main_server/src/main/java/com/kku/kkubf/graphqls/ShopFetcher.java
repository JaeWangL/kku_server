package com.kku.kkubf.graphqls;

import com.github.f4b6a3.ulid.UlidCreator;
import com.kku.kkubf.codegen.types.PetAllowedLocationEnum;
import com.kku.kkubf.codegen.types.Shop;
import com.kku.kkubf.codegen.types.ShopSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@DgsComponent
@RequiredArgsConstructor
public class ShopFetcher {
    private static final Faker faker = new Faker(new Locale("ko"));

    @DgsQuery
    public List<Shop> shops() {
        return getShops();
    }

    @DgsQuery
    public List<Shop> nearestShops(
        @InputArgument Double latitude, @InputArgument Double longitude,
        @InputArgument Integer distanceKm) {
        return getShops();
    }

    private List<Shop> getShops() {
        return IntStream.range(0, 10).mapToObj(i -> {
            ShopSource shopSource = ShopSource.newBuilder()
                .sourceUrl(faker.internet().url())
                .build();

            return Shop.newBuilder()
                .id(UlidCreator.getUlid().toString())
                .petAllowedLocations(PetAllowedLocationEnum.valueOf(
                    faker.options().option(PetAllowedLocationEnum.class).toString()))
                .name(faker.company().name())
                .tel(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .addressLoad(faker.address().secondaryAddress())
                .latitude(Double.parseDouble(faker.address().latitude()))
                .longitude(Double.parseDouble(faker.address().longitude()))
                .thumbnails(List.of(faker.internet().image()))
                .source(shopSource)
                .build();
        }).collect(Collectors.toList());
    }
}
