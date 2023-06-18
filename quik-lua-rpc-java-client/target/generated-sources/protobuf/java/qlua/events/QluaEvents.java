// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: qlua/rpc/qlua_events.proto

package qlua.events;

public final class QluaEvents {
  private QluaEvents() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code qlua.events.EventType}
   */
  public enum EventType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>EVENT_TYPE_UNKNOWN = 0;</code>
     */
    EVENT_TYPE_UNKNOWN(0),
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>PUBLISHER_ONLINE = 1;</code>
     */
    PUBLISHER_ONLINE(1),
    /**
     * <code>ON_STOP = 2;</code>
     */
    ON_STOP(2),
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_CLOSE = 3;</code>
     */
    ON_CLOSE(3),
    /**
     * <code>ON_CONNECTED = 4;</code>
     */
    ON_CONNECTED(4),
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_DISCONNECTED = 5;</code>
     */
    ON_DISCONNECTED(5),
    /**
     * <code>ON_FIRM = 6;</code>
     */
    ON_FIRM(6),
    /**
     * <code>ON_ALL_TRADE = 7;</code>
     */
    ON_ALL_TRADE(7),
    /**
     * <code>ON_TRADE = 8;</code>
     */
    ON_TRADE(8),
    /**
     * <code>ON_ORDER = 9;</code>
     */
    ON_ORDER(9),
    /**
     * <code>ON_ACCOUNT_BALANCE = 10;</code>
     */
    ON_ACCOUNT_BALANCE(10),
    /**
     * <code>ON_FUTURES_LIMIT_CHANGE = 11;</code>
     */
    ON_FUTURES_LIMIT_CHANGE(11),
    /**
     * <code>ON_FUTURES_LIMIT_DELETE = 12;</code>
     */
    ON_FUTURES_LIMIT_DELETE(12),
    /**
     * <code>ON_FUTURES_CLIENT_HOLDING = 13;</code>
     */
    ON_FUTURES_CLIENT_HOLDING(13),
    /**
     * <code>ON_MONEY_LIMIT = 14;</code>
     */
    ON_MONEY_LIMIT(14),
    /**
     * <code>ON_MONEY_LIMIT_DELETE = 15;</code>
     */
    ON_MONEY_LIMIT_DELETE(15),
    /**
     * <code>ON_DEPO_LIMIT = 16;</code>
     */
    ON_DEPO_LIMIT(16),
    /**
     * <code>ON_DEPO_LIMIT_DELETE = 17;</code>
     */
    ON_DEPO_LIMIT_DELETE(17),
    /**
     * <code>ON_ACCOUNT_POSITION = 18;</code>
     */
    ON_ACCOUNT_POSITION(18),
    /**
     * <code>ON_NEG_DEAL = 19;</code>
     */
    ON_NEG_DEAL(19),
    /**
     * <code>ON_NEG_TRADE = 20;</code>
     */
    ON_NEG_TRADE(20),
    /**
     * <code>ON_STOP_ORDER = 21;</code>
     */
    ON_STOP_ORDER(21),
    /**
     * <code>ON_TRANS_REPLY = 22;</code>
     */
    ON_TRANS_REPLY(22),
    /**
     * <code>ON_PARAM = 23;</code>
     */
    ON_PARAM(23),
    /**
     * <code>ON_QUOTE = 24;</code>
     */
    ON_QUOTE(24),
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_CLEAN_UP = 25;</code>
     */
    ON_CLEAN_UP(25),
    /**
     * <code>ON_DATA_SOURCE_UPDATE = 26;</code>
     */
    ON_DATA_SOURCE_UPDATE(26),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>EVENT_TYPE_UNKNOWN = 0;</code>
     */
    public static final int EVENT_TYPE_UNKNOWN_VALUE = 0;
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>PUBLISHER_ONLINE = 1;</code>
     */
    public static final int PUBLISHER_ONLINE_VALUE = 1;
    /**
     * <code>ON_STOP = 2;</code>
     */
    public static final int ON_STOP_VALUE = 2;
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_CLOSE = 3;</code>
     */
    public static final int ON_CLOSE_VALUE = 3;
    /**
     * <code>ON_CONNECTED = 4;</code>
     */
    public static final int ON_CONNECTED_VALUE = 4;
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_DISCONNECTED = 5;</code>
     */
    public static final int ON_DISCONNECTED_VALUE = 5;
    /**
     * <code>ON_FIRM = 6;</code>
     */
    public static final int ON_FIRM_VALUE = 6;
    /**
     * <code>ON_ALL_TRADE = 7;</code>
     */
    public static final int ON_ALL_TRADE_VALUE = 7;
    /**
     * <code>ON_TRADE = 8;</code>
     */
    public static final int ON_TRADE_VALUE = 8;
    /**
     * <code>ON_ORDER = 9;</code>
     */
    public static final int ON_ORDER_VALUE = 9;
    /**
     * <code>ON_ACCOUNT_BALANCE = 10;</code>
     */
    public static final int ON_ACCOUNT_BALANCE_VALUE = 10;
    /**
     * <code>ON_FUTURES_LIMIT_CHANGE = 11;</code>
     */
    public static final int ON_FUTURES_LIMIT_CHANGE_VALUE = 11;
    /**
     * <code>ON_FUTURES_LIMIT_DELETE = 12;</code>
     */
    public static final int ON_FUTURES_LIMIT_DELETE_VALUE = 12;
    /**
     * <code>ON_FUTURES_CLIENT_HOLDING = 13;</code>
     */
    public static final int ON_FUTURES_CLIENT_HOLDING_VALUE = 13;
    /**
     * <code>ON_MONEY_LIMIT = 14;</code>
     */
    public static final int ON_MONEY_LIMIT_VALUE = 14;
    /**
     * <code>ON_MONEY_LIMIT_DELETE = 15;</code>
     */
    public static final int ON_MONEY_LIMIT_DELETE_VALUE = 15;
    /**
     * <code>ON_DEPO_LIMIT = 16;</code>
     */
    public static final int ON_DEPO_LIMIT_VALUE = 16;
    /**
     * <code>ON_DEPO_LIMIT_DELETE = 17;</code>
     */
    public static final int ON_DEPO_LIMIT_DELETE_VALUE = 17;
    /**
     * <code>ON_ACCOUNT_POSITION = 18;</code>
     */
    public static final int ON_ACCOUNT_POSITION_VALUE = 18;
    /**
     * <code>ON_NEG_DEAL = 19;</code>
     */
    public static final int ON_NEG_DEAL_VALUE = 19;
    /**
     * <code>ON_NEG_TRADE = 20;</code>
     */
    public static final int ON_NEG_TRADE_VALUE = 20;
    /**
     * <code>ON_STOP_ORDER = 21;</code>
     */
    public static final int ON_STOP_ORDER_VALUE = 21;
    /**
     * <code>ON_TRANS_REPLY = 22;</code>
     */
    public static final int ON_TRANS_REPLY_VALUE = 22;
    /**
     * <code>ON_PARAM = 23;</code>
     */
    public static final int ON_PARAM_VALUE = 23;
    /**
     * <code>ON_QUOTE = 24;</code>
     */
    public static final int ON_QUOTE_VALUE = 24;
    /**
     * <pre>
     * no data attached
     * </pre>
     *
     * <code>ON_CLEAN_UP = 25;</code>
     */
    public static final int ON_CLEAN_UP_VALUE = 25;
    /**
     * <code>ON_DATA_SOURCE_UPDATE = 26;</code>
     */
    public static final int ON_DATA_SOURCE_UPDATE_VALUE = 26;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static EventType valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static EventType forNumber(int value) {
      switch (value) {
        case 0: return EVENT_TYPE_UNKNOWN;
        case 1: return PUBLISHER_ONLINE;
        case 2: return ON_STOP;
        case 3: return ON_CLOSE;
        case 4: return ON_CONNECTED;
        case 5: return ON_DISCONNECTED;
        case 6: return ON_FIRM;
        case 7: return ON_ALL_TRADE;
        case 8: return ON_TRADE;
        case 9: return ON_ORDER;
        case 10: return ON_ACCOUNT_BALANCE;
        case 11: return ON_FUTURES_LIMIT_CHANGE;
        case 12: return ON_FUTURES_LIMIT_DELETE;
        case 13: return ON_FUTURES_CLIENT_HOLDING;
        case 14: return ON_MONEY_LIMIT;
        case 15: return ON_MONEY_LIMIT_DELETE;
        case 16: return ON_DEPO_LIMIT;
        case 17: return ON_DEPO_LIMIT_DELETE;
        case 18: return ON_ACCOUNT_POSITION;
        case 19: return ON_NEG_DEAL;
        case 20: return ON_NEG_TRADE;
        case 21: return ON_STOP_ORDER;
        case 22: return ON_TRANS_REPLY;
        case 23: return ON_PARAM;
        case 24: return ON_QUOTE;
        case 25: return ON_CLEAN_UP;
        case 26: return ON_DATA_SOURCE_UPDATE;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<EventType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        EventType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<EventType>() {
            public EventType findValueByNumber(int number) {
              return EventType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return qlua.events.QluaEvents.getDescriptor().getEnumTypes().get(0);
    }

    private static final EventType[] VALUES = values();

    public static EventType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private EventType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:qlua.events.EventType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032qlua/rpc/qlua_events.proto\022\013qlua.event" +
      "s*\256\004\n\tEventType\022\026\n\022EVENT_TYPE_UNKNOWN\020\000\022" +
      "\024\n\020PUBLISHER_ONLINE\020\001\022\013\n\007ON_STOP\020\002\022\014\n\010ON" +
      "_CLOSE\020\003\022\020\n\014ON_CONNECTED\020\004\022\023\n\017ON_DISCONN" +
      "ECTED\020\005\022\013\n\007ON_FIRM\020\006\022\020\n\014ON_ALL_TRADE\020\007\022\014" +
      "\n\010ON_TRADE\020\010\022\014\n\010ON_ORDER\020\t\022\026\n\022ON_ACCOUNT" +
      "_BALANCE\020\n\022\033\n\027ON_FUTURES_LIMIT_CHANGE\020\013\022" +
      "\033\n\027ON_FUTURES_LIMIT_DELETE\020\014\022\035\n\031ON_FUTUR" +
      "ES_CLIENT_HOLDING\020\r\022\022\n\016ON_MONEY_LIMIT\020\016\022" +
      "\031\n\025ON_MONEY_LIMIT_DELETE\020\017\022\021\n\rON_DEPO_LI" +
      "MIT\020\020\022\030\n\024ON_DEPO_LIMIT_DELETE\020\021\022\027\n\023ON_AC" +
      "COUNT_POSITION\020\022\022\017\n\013ON_NEG_DEAL\020\023\022\020\n\014ON_" +
      "NEG_TRADE\020\024\022\021\n\rON_STOP_ORDER\020\025\022\022\n\016ON_TRA" +
      "NS_REPLY\020\026\022\014\n\010ON_PARAM\020\027\022\014\n\010ON_QUOTE\020\030\022\017" +
      "\n\013ON_CLEAN_UP\020\031\022\031\n\025ON_DATA_SOURCE_UPDATE" +
      "\020\032b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}