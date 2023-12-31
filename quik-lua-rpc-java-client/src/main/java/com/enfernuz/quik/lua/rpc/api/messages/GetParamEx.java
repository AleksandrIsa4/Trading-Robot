package com.enfernuz.quik.lua.rpc.api.messages;

import com.enfernuz.quik.lua.rpc.api.RemoteProcedure;
import com.enfernuz.quik.lua.rpc.api.RpcArgs;
import com.enfernuz.quik.lua.rpc.api.RpcResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

public final class GetParamEx implements RemoteProcedure {

    private GetParamEx() {}

    @JsonPropertyOrder({Args.CLASS_CODE, Args.SEC_CODE, Args.PARAM_NAME})
    @EqualsAndHashCode
    public static final class Args implements RpcArgs<GetParamEx> {

        private static final String CLASS_CODE = "class_code";
        private static final String SEC_CODE = "sec_code";
        private static final String PARAM_NAME = "param_name";

        @JsonProperty(CLASS_CODE)
        private final String classCode;

        @JsonProperty(SEC_CODE)
        private final String secCode;

        @JsonProperty(PARAM_NAME)
        private final String paramName;

        @JsonIgnore
        public String getClassCode() {
            return classCode;
        }

        @JsonIgnore
        public String getSecCode() {
            return secCode;
        }

        @JsonIgnore
        public String getParamName() {
            return paramName;
        }

        @Builder
        public Args(@NonNull final String classCode, @NonNull final String secCode, @NonNull final String paramName) {
            this.classCode = classCode;
            this.secCode = secCode;
            this.paramName = paramName;
        }

        @NotNull
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add(CLASS_CODE, classCode)
                    .add(SEC_CODE, secCode)
                    .add(PARAM_NAME, paramName)
                    .toString();
        }
    }

    @Value
    public static class Result implements RpcResult<GetParamEx> {

        private static final String PARAM_EX = "param_ex";

        ParamEx paramEx;

        @JsonCreator
        public Result(@JsonProperty(value = PARAM_EX, required = true) @NonNull final ParamEx paramEx) {
            this.paramEx = paramEx;
        }

        @NotNull
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add(PARAM_EX, paramEx)
                    .toString();
        }
    }

    @Value
    public static class ParamEx {

        private static final String PARAM_TYPE = "param_type";
        private static final String PARAM_VALUE = "param_value";
        private static final String PARAM_IMAGE = "param_image";
        private static final String RESULT = "result";

        String paramType;
        String paramValue;
        String paramImage;
        String result;

        @JsonCreator
        @Builder
        private ParamEx(@JsonProperty(PARAM_TYPE) final String paramType,
                        @JsonProperty(PARAM_VALUE) final String paramValue,
                        @JsonProperty(PARAM_IMAGE) final String paramImage,
                        @JsonProperty(RESULT) final String result) {

            this.paramType = paramType;
            this.paramValue = paramValue;
            this.paramImage = paramImage;
            this.result = result;
        }

        @NotNull
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add(PARAM_TYPE, paramType)
                    .add(PARAM_VALUE, paramValue)
                    .add(PARAM_IMAGE, paramImage)
                    .add(RESULT, result)
                    .toString();
        }
    }
}
