// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: qlua/rpc/RGB.proto

package qlua.rpc;

public final class RGB {
  private RGB() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ArgsOrBuilder extends
      // @@protoc_insertion_point(interface_extends:qlua.rpc.RGB.Args)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 red = 1;</code>
     * @return The red.
     */
    int getRed();

    /**
     * <code>int32 green = 2;</code>
     * @return The green.
     */
    int getGreen();

    /**
     * <code>int32 blue = 3;</code>
     * @return The blue.
     */
    int getBlue();
  }
  /**
   * <pre>
   * NB: на самом деле, библиотечная функция RGB должна называться BGR, ибо она выдаёт числа именно в этом формате. В SetColor, однако, тоже ожидается цвет в формате BGR, так что это не баг, а фича.
   * </pre>
   *
   * Protobuf type {@code qlua.rpc.RGB.Args}
   */
  public static final class Args extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:qlua.rpc.RGB.Args)
      ArgsOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Args.newBuilder() to construct.
    private Args(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Args() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Args();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Args_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Args_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              qlua.rpc.RGB.Args.class, qlua.rpc.RGB.Args.Builder.class);
    }

    public static final int RED_FIELD_NUMBER = 1;
    private int red_ = 0;
    /**
     * <code>int32 red = 1;</code>
     * @return The red.
     */
    @java.lang.Override
    public int getRed() {
      return red_;
    }

    public static final int GREEN_FIELD_NUMBER = 2;
    private int green_ = 0;
    /**
     * <code>int32 green = 2;</code>
     * @return The green.
     */
    @java.lang.Override
    public int getGreen() {
      return green_;
    }

    public static final int BLUE_FIELD_NUMBER = 3;
    private int blue_ = 0;
    /**
     * <code>int32 blue = 3;</code>
     * @return The blue.
     */
    @java.lang.Override
    public int getBlue() {
      return blue_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (red_ != 0) {
        output.writeInt32(1, red_);
      }
      if (green_ != 0) {
        output.writeInt32(2, green_);
      }
      if (blue_ != 0) {
        output.writeInt32(3, blue_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (red_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, red_);
      }
      if (green_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, green_);
      }
      if (blue_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, blue_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof qlua.rpc.RGB.Args)) {
        return super.equals(obj);
      }
      qlua.rpc.RGB.Args other = (qlua.rpc.RGB.Args) obj;

      if (getRed()
          != other.getRed()) return false;
      if (getGreen()
          != other.getGreen()) return false;
      if (getBlue()
          != other.getBlue()) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + RED_FIELD_NUMBER;
      hash = (53 * hash) + getRed();
      hash = (37 * hash) + GREEN_FIELD_NUMBER;
      hash = (53 * hash) + getGreen();
      hash = (37 * hash) + BLUE_FIELD_NUMBER;
      hash = (53 * hash) + getBlue();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static qlua.rpc.RGB.Args parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Args parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Args parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static qlua.rpc.RGB.Args parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static qlua.rpc.RGB.Args parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static qlua.rpc.RGB.Args parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(qlua.rpc.RGB.Args prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * NB: на самом деле, библиотечная функция RGB должна называться BGR, ибо она выдаёт числа именно в этом формате. В SetColor, однако, тоже ожидается цвет в формате BGR, так что это не баг, а фича.
     * </pre>
     *
     * Protobuf type {@code qlua.rpc.RGB.Args}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:qlua.rpc.RGB.Args)
        qlua.rpc.RGB.ArgsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Args_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Args_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                qlua.rpc.RGB.Args.class, qlua.rpc.RGB.Args.Builder.class);
      }

      // Construct using qlua.rpc.RGB.Args.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        red_ = 0;
        green_ = 0;
        blue_ = 0;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Args_descriptor;
      }

      @java.lang.Override
      public qlua.rpc.RGB.Args getDefaultInstanceForType() {
        return qlua.rpc.RGB.Args.getDefaultInstance();
      }

      @java.lang.Override
      public qlua.rpc.RGB.Args build() {
        qlua.rpc.RGB.Args result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public qlua.rpc.RGB.Args buildPartial() {
        qlua.rpc.RGB.Args result = new qlua.rpc.RGB.Args(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(qlua.rpc.RGB.Args result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.red_ = red_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.green_ = green_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.blue_ = blue_;
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof qlua.rpc.RGB.Args) {
          return mergeFrom((qlua.rpc.RGB.Args)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(qlua.rpc.RGB.Args other) {
        if (other == qlua.rpc.RGB.Args.getDefaultInstance()) return this;
        if (other.getRed() != 0) {
          setRed(other.getRed());
        }
        if (other.getGreen() != 0) {
          setGreen(other.getGreen());
        }
        if (other.getBlue() != 0) {
          setBlue(other.getBlue());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                red_ = input.readInt32();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              case 16: {
                green_ = input.readInt32();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
              case 24: {
                blue_ = input.readInt32();
                bitField0_ |= 0x00000004;
                break;
              } // case 24
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private int red_ ;
      /**
       * <code>int32 red = 1;</code>
       * @return The red.
       */
      @java.lang.Override
      public int getRed() {
        return red_;
      }
      /**
       * <code>int32 red = 1;</code>
       * @param value The red to set.
       * @return This builder for chaining.
       */
      public Builder setRed(int value) {

        red_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>int32 red = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearRed() {
        bitField0_ = (bitField0_ & ~0x00000001);
        red_ = 0;
        onChanged();
        return this;
      }

      private int green_ ;
      /**
       * <code>int32 green = 2;</code>
       * @return The green.
       */
      @java.lang.Override
      public int getGreen() {
        return green_;
      }
      /**
       * <code>int32 green = 2;</code>
       * @param value The green to set.
       * @return This builder for chaining.
       */
      public Builder setGreen(int value) {

        green_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>int32 green = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearGreen() {
        bitField0_ = (bitField0_ & ~0x00000002);
        green_ = 0;
        onChanged();
        return this;
      }

      private int blue_ ;
      /**
       * <code>int32 blue = 3;</code>
       * @return The blue.
       */
      @java.lang.Override
      public int getBlue() {
        return blue_;
      }
      /**
       * <code>int32 blue = 3;</code>
       * @param value The blue to set.
       * @return This builder for chaining.
       */
      public Builder setBlue(int value) {

        blue_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>int32 blue = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearBlue() {
        bitField0_ = (bitField0_ & ~0x00000004);
        blue_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:qlua.rpc.RGB.Args)
    }

    // @@protoc_insertion_point(class_scope:qlua.rpc.RGB.Args)
    private static final qlua.rpc.RGB.Args DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new qlua.rpc.RGB.Args();
    }

    public static qlua.rpc.RGB.Args getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Args>
        PARSER = new com.google.protobuf.AbstractParser<Args>() {
      @java.lang.Override
      public Args parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<Args> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Args> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public qlua.rpc.RGB.Args getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface ResultOrBuilder extends
      // @@protoc_insertion_point(interface_extends:qlua.rpc.RGB.Result)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 result = 1;</code>
     * @return The result.
     */
    int getResult();
  }
  /**
   * Protobuf type {@code qlua.rpc.RGB.Result}
   */
  public static final class Result extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:qlua.rpc.RGB.Result)
      ResultOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Result.newBuilder() to construct.
    private Result(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Result() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Result();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Result_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Result_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              qlua.rpc.RGB.Result.class, qlua.rpc.RGB.Result.Builder.class);
    }

    public static final int RESULT_FIELD_NUMBER = 1;
    private int result_ = 0;
    /**
     * <code>int32 result = 1;</code>
     * @return The result.
     */
    @java.lang.Override
    public int getResult() {
      return result_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (result_ != 0) {
        output.writeInt32(1, result_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (result_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, result_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof qlua.rpc.RGB.Result)) {
        return super.equals(obj);
      }
      qlua.rpc.RGB.Result other = (qlua.rpc.RGB.Result) obj;

      if (getResult()
          != other.getResult()) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + RESULT_FIELD_NUMBER;
      hash = (53 * hash) + getResult();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static qlua.rpc.RGB.Result parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Result parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static qlua.rpc.RGB.Result parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static qlua.rpc.RGB.Result parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static qlua.rpc.RGB.Result parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static qlua.rpc.RGB.Result parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(qlua.rpc.RGB.Result prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code qlua.rpc.RGB.Result}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:qlua.rpc.RGB.Result)
        qlua.rpc.RGB.ResultOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Result_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Result_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                qlua.rpc.RGB.Result.class, qlua.rpc.RGB.Result.Builder.class);
      }

      // Construct using qlua.rpc.RGB.Result.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        result_ = 0;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return qlua.rpc.RGB.internal_static_qlua_rpc_RGB_Result_descriptor;
      }

      @java.lang.Override
      public qlua.rpc.RGB.Result getDefaultInstanceForType() {
        return qlua.rpc.RGB.Result.getDefaultInstance();
      }

      @java.lang.Override
      public qlua.rpc.RGB.Result build() {
        qlua.rpc.RGB.Result result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public qlua.rpc.RGB.Result buildPartial() {
        qlua.rpc.RGB.Result result = new qlua.rpc.RGB.Result(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(qlua.rpc.RGB.Result result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.result_ = result_;
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof qlua.rpc.RGB.Result) {
          return mergeFrom((qlua.rpc.RGB.Result)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(qlua.rpc.RGB.Result other) {
        if (other == qlua.rpc.RGB.Result.getDefaultInstance()) return this;
        if (other.getResult() != 0) {
          setResult(other.getResult());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                result_ = input.readInt32();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private int result_ ;
      /**
       * <code>int32 result = 1;</code>
       * @return The result.
       */
      @java.lang.Override
      public int getResult() {
        return result_;
      }
      /**
       * <code>int32 result = 1;</code>
       * @param value The result to set.
       * @return This builder for chaining.
       */
      public Builder setResult(int value) {

        result_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>int32 result = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearResult() {
        bitField0_ = (bitField0_ & ~0x00000001);
        result_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:qlua.rpc.RGB.Result)
    }

    // @@protoc_insertion_point(class_scope:qlua.rpc.RGB.Result)
    private static final qlua.rpc.RGB.Result DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new qlua.rpc.RGB.Result();
    }

    public static qlua.rpc.RGB.Result getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Result>
        PARSER = new com.google.protobuf.AbstractParser<Result>() {
      @java.lang.Override
      public Result parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<Result> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Result> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public qlua.rpc.RGB.Result getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_qlua_rpc_RGB_Args_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_qlua_rpc_RGB_Args_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_qlua_rpc_RGB_Result_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_qlua_rpc_RGB_Result_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022qlua/rpc/RGB.proto\022\014qlua.rpc.RGB\"0\n\004Ar" +
      "gs\022\013\n\003red\030\001 \001(\005\022\r\n\005green\030\002 \001(\005\022\014\n\004blue\030\003" +
      " \001(\005\"\030\n\006Result\022\016\n\006result\030\001 \001(\005B\014\n\010qlua.r" +
      "pcH\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_qlua_rpc_RGB_Args_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_qlua_rpc_RGB_Args_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_qlua_rpc_RGB_Args_descriptor,
        new java.lang.String[] { "Red", "Green", "Blue", });
    internal_static_qlua_rpc_RGB_Result_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_qlua_rpc_RGB_Result_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_qlua_rpc_RGB_Result_descriptor,
        new java.lang.String[] { "Result", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
