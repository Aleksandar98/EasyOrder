package com.example.eeasyorder.data.remote.dto;

import android.os.Build;
import android.util.Log;

import com.example.eeasyorder.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDto {

    public List<Venue> venues;

    public class Area{
        public int id;
        public int type_id;
        public String reference_type;
        public int venue_id;
        public int menu_id;
        public int singular_point_id;
        public Object pos_id;
        public String name;
        public boolean use_tablet;
        public int state;
        public int is_table_supported;
        public Properties properties;
        public ArrayList<OrderType> order_types;
        public Object table_pos_id;
        public Tablet tablet;
        public ArrayList<DeliveryAreaMap> delivery_area_maps;
        public ArrayList<Object> delivery_times;
    }

    public class AvailablePaymentMethod{
        public int id;
        public int payment_method_id;
        public int payment_method_type_id;
        public String payment_method_brand;
        public int payment_processor_type_id;
        public int payment_processor_id;
        public Properties properties;
    }

    public class Country{
        public int id;
        public int currency_id;
        public String name;
        public String code;
        public String code_alpha3;
        public String code_numeric;
        public String calling_code;
        public String distance_unit;
        public boolean is_address_number_first;
        public CurrencySettings currency_settings;
        public ArrayList<Object> supported_travel_modes;
        public ArrayList<AvailablePaymentMethod> available_payment_methods;
    }

    public class Currency{
        public int id;
        public String code;
        public String code_numeric;
        public String symbol;
        public double rounding_unit;
        public double rounding_unit_tip;
    }

    public class CurrencySettings{
        public boolean currency_space;
        public String decimal_separator;
        public String thousands_separator;
        public String symbol_position;
    }

    public class DeliveryAreaMap{
        public int id;
        public int area_id;
        public int delivery_fee_id;
        public Object minimal_order_amount;
        public String name;
        public ArrayList<ArrayList<ArrayList<Double>>> coordinate_polygon;
        public Object radius;
        public int state;
        public String created_at;
        public String updated_at;
    }

    public class Integration{
        public int id;
        public int type_id;
        public String reference_type;
        public boolean is_windows_pos;
        public int pos_modifier_group_type;
        public boolean support_combo_meals;
        public int max_comment_limit;
    }

    public class OrderType{
        public int type_id;
        public int price_configuration;
        public int id;
        public String reference_type;
        public int state;
        public boolean is_table_supported;
        public boolean external_channel_only;
    }

    public class PriceConfiguration{
        public ArrayList<OrderType> order_types;
        public ArrayList<Object> external_channels;
    }

    public class Properties{
        public boolean pos_wait_until_paid;
        public int max_cash_amount;
        public String location_type;
    }


    public class Tablet{
        public int id;
        public int venue_id;
        public String token;
        public String name;
        public int use_notifications;
        public boolean show_kiosk_orders;
        public int kiosk_notification_type;
        public Object middleware_endpoint;
        public Object pls_name;
        public int state;
        public boolean is_online;
        public String last_request_at;
        public boolean failure_reported;
        public boolean is_critical;
        public ArrayList<Integer> area_ids;
    }

    public class Timezone{
        public String name;
        public String offset;
    }

    public class Translations{
        public Object description;
        public Object kiosk_receipt_footer;
        public Object welcome_message;
    }

    public class Venue{
        public double distance;
        public double distance_in_miles;
        public Venue2 venue;
    }

    public class Image{
        public String fullsize;
        public String thumbnail_medium;
        public String thumbnail_small;
    }

    public class Venue2{
        public int id;
        public String name;
        public String code;
        public String parent_type;
        public int parent_id;
        public int company_id;
        public Object chain_id;
        public int country_id;
        public int currency_id;
        public int language_id;
        public Object store_group_id;
        public int brand_id;
        public Timezone timezone;
        public String description;
        public String kiosk_receipt_footer;
        public String imprint;
        public String welcome_message;
        public Translations translations;
        public String address;
        public int state;
        public String city;
        public String zip;
        public double latitude;
        public double longitude;
        public String tax_number;
        public String phone;
        public PriceConfiguration price_configuration;
        public ArrayList<Integer> used_price_configurations;
        public String cuisine;
        public boolean is_shown_in_directory;
        public boolean allow_item_comments;
        public int tip_default;
        public int tip_max;
        public int service_charge;
        public int pickup_time;
        public boolean is_pickup_on_open_allowed;
        public String calculation_method;
        public Image image;
        public ArrayList<Object> available_payment_methods;
        public Country country;
        public Currency currency;
        public ArrayList<OrderType> order_types;
        public ArrayList<Object> temporary_updated_entities;
        public ArrayList<ServingTime> serving_times;
        public boolean is_open;
        public boolean will_open;
        public boolean has_discounts;
        public boolean has_loyalty;
        public boolean has_promotions;
        public boolean has_delivery_integration;
        public Object delivery_integration_properties;
        public String delivery_travel_type;
        public boolean is_calculated_delivery_buffer_used;
        public boolean is_monitoring_enabled;
        public boolean is_threeds_enabled;
        public boolean is_fraud_detection_enabled;
        public boolean is_smart_orders_enabled;
        public boolean use_pos_order_number;
        public boolean is_order_regret_enabled;
        public boolean is_billable;
        public int default_delivery_buffer;
        public Integration integration;
        public String created_at;
        public String updated_at;
        public ArrayList<Area> areas;
    }

    public class ServingTime{
        public int id;
        public int type_id;
        public String reference_type;
        public String time_from;
        public String time_to;
        public Object delivery_at;
        public ArrayList<Integer> days;
    }

//    @Override
//    public String toString() {
//        return "RestaurantDto{" +
//                "venues.size=" + venues.size() +
//                '}';
//    }

    private Restaurant toRestaurant(
            String name, String description, boolean isOpen, String welcomeMessage, String imageUrl, String distance,
            String address,
            String serving_times
    ){
        return new Restaurant(
                name,
                description,
                isOpen,
                welcomeMessage,
                imageUrl,
                distance,
                address,
                serving_times
        );
    }

    public List<Restaurant> toRestaurants(){

        List<Restaurant> list = new ArrayList<>(venues.size());
        Log.d("TAG", "onNext: venues"+venues.size());
        for (Venue venue : venues) {
            String imageUrl = "";
            String servingTimeString = "";
            String distanceString = "";
            if(venue.venue.image != null){
                imageUrl = venue.venue.image.thumbnail_medium;
            }
            if(venue.venue.serving_times.size() != 0){
                ServingTime servingTime = venue.venue.serving_times.get(0);
                if (servingTime.time_from != null && servingTime.time_to!= null && servingTime.reference_type != null)
                servingTimeString = servingTime.time_from + " - " + servingTime.time_to;
                else if (servingTime.reference_type != null){
                    servingTimeString = "No information about time, works on " + servingTime.reference_type;
                }
            }

            if(venue.distance > 1000)
                distanceString = Math.round(venue.distance) / 1000 + "km";
            else
                distanceString = Math.round(venue.distance) + "m";
            list.add(
                    new Restaurant(
                            venue.venue.name,
                            venue.venue.description,
                            venue.venue.is_open,
                            venue.venue.welcome_message,
                            imageUrl,
                            distanceString,
                            venue.venue.address,
                            servingTimeString)
            );
        }

        Log.d("TAG", "onNext: list"+list.size());
            return list;

    }
}
