// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: design.proto at 68:1
package au.com.codeka.warworlds.common.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import okio.ByteString;

public final class Designs extends Message<Designs, Designs.Builder> {
  public static final ProtoAdapter<Designs> ADAPTER = new ProtoAdapter_Designs();

  private static final long serialVersionUID = 0L;

  @WireField(
      tag = 1,
      adapter = "au.com.codeka.warworlds.common.proto.Design#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<Design> designs;

  public Designs(List<Design> designs) {
    this(designs, ByteString.EMPTY);
  }

  public Designs(List<Design> designs, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.designs = Internal.immutableCopyOf("designs", designs);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.designs = Internal.copyOf("designs", designs);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof Designs)) return false;
    Designs o = (Designs) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(designs, o.designs);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (designs != null ? designs.hashCode() : 1);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (designs != null) builder.append(", designs=").append(designs);
    return builder.replace(0, 2, "Designs{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<Designs, Builder> {
    public List<Design> designs;

    public Builder() {
      designs = Internal.newMutableList();
    }

    public Builder designs(List<Design> designs) {
      Internal.checkElementsNotNull(designs);
      this.designs = designs;
      return this;
    }

    @Override
    public Designs build() {
      return new Designs(designs, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_Designs extends ProtoAdapter<Designs> {
    ProtoAdapter_Designs() {
      super(FieldEncoding.LENGTH_DELIMITED, Designs.class);
    }

    @Override
    public int encodedSize(Designs value) {
      return Design.ADAPTER.asRepeated().encodedSizeWithTag(1, value.designs)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, Designs value) throws IOException {
      if (value.designs != null) Design.ADAPTER.asRepeated().encodeWithTag(writer, 1, value.designs);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public Designs decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.designs.add(Design.ADAPTER.decode(reader)); break;
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public Designs redact(Designs value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.designs, Design.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
