package com.westat

/**
 * Created by Owner on 11/17/2016.
 */

trait CodeType {
  def displayValue : String
}

trait UOM extends CodeType {
  def unitRatio : Int
}
/*TMetre         = Double;
TInch          = Double;
TCentimetre    = Double;
TPica          = Double;
TMillimetre    = Double;
TBigPoint      = Double;
TPrintersPoint = Double;
TFut           = Currency;
TPercent       = Double;
  UnitNames: array[TUnit] of WideString =('m', 'in', 'cm', 'pc', 'mm', 'pt', 'pp', 'fu');

  UnitRatios: array[TUnit] of Integer = (
    289080000,
      7342632,
      2890800,
      1219200,
       289080,
       101981,
       101600,
            1);
*/

case object Metre extends UOM {
  def displayValue : String = { "m" }
  def unitRatio : Int = { 289080000 }
}
case object Inch extends UOM {
  def displayValue : String = { "in" }
  def unitRatio : Int = { 7342632 }
}
case object Centimetre extends UOM {
  def displayValue : String = { "cm" }
  def unitRatio : Int = { 2890800 }
}
case object Pica extends UOM {
  def displayValue : String = { "pc" }
  def unitRatio : Int = { 1219200 }
}
case object Millimetre extends UOM {
  def displayValue : String = { "mm" }
  def unitRatio : Int = { 289080 }
}
case object PrintersPoint extends UOM {
  def displayValue : String = { "pp" }
  def unitRatio : Int = { 101600 }
}
case object Fut extends UOM {
  def displayValue : String = { "fu" }
  def unitRatio : Int = { 1 }
}

/*
  Fut is base underlying unit that everything gets converted to (device units)
  class makes an object of specified uom and value
  object makes conversions and provides conversion
    primarily used to convert strings to values ie: Length.dimension("0.14in") to a double
    can then do calcs with the double and then ask for
 */
case class Length(uom : UOM, value : Double) {
  private val LDPI = 7342632
  def asFut : Double = {
     value * uom.unitRatio
  }
  def asDeviceUnits : Long = {
//    println(s"asDeviceUnits for $value asFut is "+asFut.toLong)
    val s : Double = (asFut / LDPI) * 100
    s.toLong
  }
  def fromDeviceUnits(newvalue : Long) : Length = {
//    println(s"fromDeviceUnits called for $newvalue")
    Length(Fut, (newvalue.toDouble / 100) * LDPI)
  }
  def +(v : Length) : Length = {
    fromDeviceUnits(asDeviceUnits + v.asDeviceUnits)
  }
  def -(v : Length) : Length = {
    fromDeviceUnits(asDeviceUnits - v.asDeviceUnits)
  }
  def asMetres : Double = {
    asFut / Metre.unitRatio
  }
  def asInches : Double = {
    asFut / Inch.unitRatio
  }
  def asCentimetres : Double = {
    asFut / Centimetre.unitRatio
  }
  def asPicas : Double = {
    asFut / Pica.unitRatio
  }
  def asMillimetres : Double = {
    asFut / Millimetre.unitRatio
  }
  def asPrintersPoints : Double = {
    asFut / PrintersPoint.unitRatio
  }
  def translateUOMs : String = {
    val mm = asMillimetres
    val cm = asCentimetres
    val m = asMetres
    val in = asInches
    val pc = asPicas
    val pp = asPrintersPoints
    s"$this as mm:$mm cm:$cm m:$m in:$in pc:$pc pp:$pp"
  }
}

// primarily serves to convert strings to Double
object Length {
  def test = {
    var d = dimension("25.4mm")
    println(s"dimension of 25.4mm is $d and deviceUnits is "+d.asDeviceUnits)
    d = dimension("2.54cm")
    println(s"dimension of 2.54cm is $d and deviceUnits is "+d.asDeviceUnits)
    d = dimension("1in")
    println(s"dimension of 1.in is $d and deviceUnits is "+d.asDeviceUnits)
    println("  and as other uoms is "+d.translateUOMs)
    d = dimension("2in")
    println(s"dimension of 2.in is $d and deviceUnits is "+d.asDeviceUnits)
    d = fromDeviceUnits(d.asDeviceUnits)
    println(s"dimension of 2in translated from devunits is $d and deviceUnits is "+d.asDeviceUnits)
    val du = d.asDeviceUnits
    d = dimension(".5in")
    d = fromDeviceUnits(du - d.asDeviceUnits)
    println(s"dimension of 2in less half inch translated from devunits is $d and deviceUnits is "+d.asDeviceUnits)
    d = dimension("1.275pc")
    println(s"dimension of 1.275pc is $d and deviceUnits is "+d.asDeviceUnits)
    d = dimension("22027896fu")
    println(s"dimension of 22027896fu is $d and deviceUnits is "+d.asDeviceUnits)
    d = dimension("3.in")
    println(s"move to 3.in is $d and deviceUnits is "+d.asDeviceUnits)
    d = add(d, ".5in")
    println(s"add .5in is $d and deviceUnits is "+d.asDeviceUnits)
    println("  and as other uoms is "+d.translateUOMs)
    d = sub(d, ".5in")
    println(s"sub .5in is $d and deviceUnits is "+d.asDeviceUnits)
    println("  and as other uoms is "+d.translateUOMs)
  }

  private def uomFromString(value : String) : UOM = {
   //  var cfnRe = "^\\d{10}$".r
   //  var addRe = """^ADD_\d{6}$""".r
//    y[TUnit] of WideString =('m', 'in', 'cm', 'pc', 'mm', 'pt', 'pp', 'fu');
    val re = "in$|cm$|pc$|mm$|pp$|fu$|m$".r
     var result : String = ""
     re.findFirstIn(value) match {
      case Some(um) => result = um
      case None =>
     }
   //  println(s"uomfromstring $value was $result")
     result match {
       case "m"  => Metre
       case "in" => Inch
       case "cm" => Centimetre
       case "pc" => Pica
       case "mm" => Millimetre
       case "pp" => PrintersPoint
       case "fu" => Fut
       case "" => Inch
     }
  }

  def dimension(value : String) : Length = {
    val uom = uomFromString(value);
    Length(uom, value.dropRight(uom.displayValue.length).toDouble)
  }

  def fromDeviceUnits(newvalue : Long) : Length = {
//    println(s"fromDeviceUnits called for $newvalue")
//    val nv = (newvalue.toDouble / 100)
//    println(s"   div by 100 is $nv and mult by fut is "+(nv * 7342632))
    Length(Fut, (newvalue.toDouble / 100) * 7342632)
  }



  // need to be able to do math via common Fut, but displayString in any uom!
  def add(d : Length, value : String) : Length = {
    val uom = uomFromString(value);
    val amt = Length(uom, value.dropRight(uom.displayValue.length).toDouble)
    val eq = d + amt
    eq
  }
  def sub(d : Length, value : String) : Length = {
    val uom = uomFromString(value);
    val amt = Length(uom, value.dropRight(uom.displayValue.length).toDouble)
    d - amt
  }
/*  const
  cPlus   = WideChar('+');
  cMinus  = WideChar('-');
  cPeriod = WideChar('.');
  cZero   = WideChar('0');
  cNine   = WideChar('9');
  DigitChars  = [cZero..cNine];
  SignChars   = [cPlus, cMinus];
  NumberChars = DigitChars + SignChars + [cPeriod];
  var
  I: Integer;
  SS: WideString;
  UnitName: WideString;
  Units: TUnit;
  ValueString: WideString;
  begin
  Result := 0;
  SS := Trim(Value);
  if SS = '' then begin
    Result := NullFut;
  Exit end;
  I := 1;
  while (I <= length(SS)) and (SS[I] in NumberChars) do
  Inc(I);
  ValueString := Copy(SS, 1, I - 1);
  UnitName := Trim(Copy(SS, I, Length(SS)));
  SS := LowerCase(UnitName);
  if SS = '' then
  Units := lunPT
  else begin
  Units := Low(TUnit);
  while (Units <= High(TUnit)) and (UnitNames[Units] <> SS) do
  Inc(Units);
  if Units > High(TUnit) then
    raise ELength.createFmt(sLengthInvalidUnits, [UnitName]) end;
  case Units of
    lunM:
    Result := Metres(ValueString);
  lunIN:
    Result := Inches(ValueString);
  lunCM:
    Result := Centimetres(ValueString);
  lunPC:
    Result := Picas(ValueString);
  lunMM:
    Result := Millimetres(ValueString);
  lunPT:
    Result := BigPoints(ValueString);
  lunPP:
    Result := PrintersPoints(ValueString);
  lunFU:
    Result := Futs(ValueString) end end;
*/
}

/*
unit uLength;

interface

uses
  Windows,
  SysUtils,
  Classes,
  Graphics,
  ComObj;

const
  UhrDpi = 7342632;
  DisplayDecimalDigits: Integer = 15; // All possible

type
  TUnit = (lunM, lunIN, lunCM, lunPC, lunMM, lunPT, lunPP, lunFU);

  TMetre         = Double;
  TInch          = Double;
  TCentimetre    = Double;
  TPica          = Double;
  TMillimetre    = Double;
  TBigPoint      = Double;
  TPrintersPoint = Double;
  TFut           = Currency;
  TPercent       = Double;

  TDeviceUnit     = type Integer;
  TDevicePoint    = type TPoint;
  TDeviceRect     = type TRect;

  TFutPoint = packed record
    X: TFut;
    Y: TFut; end;

  TFutRect = record
    case Integer of
      0: (Left, Top, Right, Bottom: TFut);
      1: (TopLeft, BottomRight: TFutPoint) end;

  TFutRects = array of TFutRect;

  CLength = class
    public
      class function AsBigPoints(const Length: TFut): TBigPoint;
      class function AsBigPointsString(const Length: TFut): WideString;
      class function AsBigPointsStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsCentimetres(const Length: TFut): TCentimetre;
      class function AsCentimetresString(const Length: TFut): WideString;
      class function AsCentimetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsDeviceUnits(const Length: TFut; const DeviceDpi: TDeviceUnit): TDeviceUnit;
      class function AsDeviceUnitsX(const Length: TFut; const canvas: TCanvas): TDeviceUnit;
      class function AsDeviceUnitsY(const Length: TFut; const canvas: TCanvas): TDeviceUnit;
      class function AsFuts(const Length: TFut): TFut;
      class function AsFutsString(const Length: TFut): WideString;
      class function AsFutsStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsInches(const Length: TFut): TInch;
      class function AsInchesString(const Length: TFut): WideString;
      class function AsInchesStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsMetres(const Length: TFut): TMetre;
      class function AsMetresString(const Length: TFut): WideString;
      class function AsMetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsMillimetres(const Length: TFut): TMillimetre;
      class function AsMillimetresString(const Length: TFut): WideString;
      class function AsMillimetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsPicas(const Length: TFut): TPica;
      class function AsPicasString(const Length: TFut): WideString;
      class function AsPicasStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsPoint(const FutPoint: TFutPoint; const DeviceDpi: TDeviceUnit): TPoint;
      class function AsPrintersPoints(const Length: TFut): TPrintersPoint;
      class function AsPrintersPointsString(const Length: TFut): WideString;
      class function AsPrintersPointsStringDigits(const Length: TFut; NumDigits: integer): WideString;
      class function AsStringUnits(const Length: TFut; const Units: TUnit): WideString;
      class function AsStringUnitDigits(const Length: TFut; const Units: TUnit; NumDigits: integer): WideString;
      class function BigPoints(const Value: TBigPoint): TFut; overload;
      class function BigPoints(const Value: String): TFut; overload;
      class function Centimetres(const Value: TCentimetre): TFut; overload;
      class function Centimetres(const Value: String): TFut; overload;
      class function DeviceUnits(X: TDeviceUnit; DeviceDpi: TDeviceUnit): TFut;
      class function DevicePoint(const X, Y: TDeviceUnit): TDevicePoint;
      class function DeviceRect(const Left, Top, Right, Bottom: TDeviceUnit): TDeviceRect;
      class function Dimension(const Value: String): TFut;
      class function FutPoint(const X, Y: TFut): TFutPoint;
      class function FutRect(const Left, Top, Right, Bottom: TFut): TFutRect;
      class function Futs(const Value: TFut): TFut; overload;
      class function Futs(const Value: String): TFut; overload;
      class function Inches(const Value: TInch): TFut; overload;
      class function Inches(const Value: String): TFut; overload;
      class function Max(const A, B: TFut): TFut;
      class function Metres(const Value: TMetre): TFut; overload;
      class function Metres(const Value: String): TFut; overload;
      class function Millimetres(const Value: TMillimetre): TFut; overload;
      class function Millimetres(const Value: String): TFut; overload;
      class function Min(const A, B: TFut): TFut;
      class function Percent(const Value: TPercent; const RefLength: TFut): TFut; overload;
      class function Percent(const Value: String; const RefLength: TFut): TFut; overload;
      class function Picas(const Value: TPica): TFut; overload;
      class function Picas(const Value: String): TFut; overload;
      class function PrintersPoints(const Value: TPrintersPoint): TFut; overload;
      class function PrintersPoints(const Value: String): TFut; overload; end;

  ELength = class(Exception);

  ICTM = interface(IInterface)
    ['{82931109-F6B9-4EE1-9CF9-12A9CD1A200F}']
      function a: Double;
      function appendTo(const ctm: ICTM): ICTM;
      function apply(const p: TFutPoint): TFutPoint;
      function asString: WideString;
      function asXForm: TXForm;
      function b: Double;
      function c: Double;
      function d: Double;
      function e: TFut;
      function f: TFut;
      function prependTo(const ctm: ICTM): ICTM;
      function rotate(const theta: Double): ICTM;
      function scale(const s: Double): ICTM; overload;
      function scale(const sx, sy: Double): ICTM; overload;
      function skewX(const theta: Double): ICTM;
      function skewY(const theta: Double): ICTM;
      function translate(const t: TFut): ICTM; overload;
      function translate(const tx, ty: TFut): ICTM; overload; end;

function explicitCTM(const a, b, c, d: Double; const e, f: TFut): ICTM;
function identityCTM: ICTM;
procedure initializeFuts(const canvas: TCanvas);

function getCTM(const canvas: TCanvas): ICTM;
procedure setCTM(const canvas: TCanvas; const ctm: ICTM);

var
  NullFut: Currency = 0;

const
  UnitNames: array[TUnit] of WideString =('m', 'in', 'cm', 'pc', 'mm', 'pt', 'pp', 'fu');

  UnitRatios: array[TUnit] of Integer = (
    289080000,
      7342632,
      2890800,
      1219200,
       289080,
       101981,
       101600,
            1);

implementation

uses
  uWideString;

resourcestring
  sLengthInvalidStringRep = 'The string "%s" is not a valid text representation of a length.';
  sLengthInvalidUnits     = 'The string "%s" is not a valid length unit specifier."';

const
  NullLength: Int64 = $8000000000000000;
  DoublePrecision = 15;

//--CLength--------------------------------------------------------------------------------------------------------------------

class function CLength.AsBigPoints(const Length: TFut): TBigPoint;
  begin
  Result := Length / UnitRatios[lunPT] end;

class function CLength.AsBigPointsString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsBigPoints(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunPT] end;

class function CLength.AsBigPointsStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsBigPoints(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunPT] end;

class function CLength.AsCentimetres(const Length: TFut): TCentimetre;
  begin
  Result := Length / UnitRatios[lunCM] end;

class function CLength.AsCentimetresString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsCentimetres(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunCM] end;

class function CLength.AsCentimetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsCentimetres(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunCM] end;

class function CLength.AsDeviceUnits(const Length: TFut; const DeviceDpi: TDeviceUnit): TDeviceUnit;
  begin
  Result := Round(Length * DeviceDpi * UnitRatios[lunFu] / UnitRatios[lunIn]) end;

class function CLength.AsDeviceUnitsX(const Length: TFut; const canvas: TCanvas): TDeviceUnit;
  begin
  Result := Round(Length * getDeviceCaps(canvas.handle, LOGPIXELSX) * UnitRatios[lunFu] / UnitRatios[lunIn]) end;

class function CLength.AsDeviceUnitsY(const Length: TFut; const canvas: TCanvas): TDeviceUnit;
  begin
  Result := Round(Length * getDeviceCaps(canvas.handle, LOGPIXELSY) * UnitRatios[lunFu] / UnitRatios[lunIn]) end;

class function CLength.AsFuts(const Length: TFut): TFut;
  begin
  Result := Length / UnitRatios[lunFU] end;

class function CLength.AsFutsString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsFuts(Length), ffFixed, DoublePrecision, 0) + UnitNames[lunFU] end;

class function CLength.AsFutsStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsFuts(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunFU] end;

class function CLength.AsInches(const Length: TFut): TInch;
  begin
  Result := Length / UnitRatios[lunIN] end;

class function CLength.AsInchesString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsInches(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunIN] end;

class function CLength.AsInchesStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsInches(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunIN] end;

class function CLength.AsMetres(const Length: TFut): TMetre;
  begin
  Result := Length / UnitRatios[lunM] end;

class function CLength.AsMetresString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsMetres(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunM] end;

class function CLength.AsMetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsMetres(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunM] end;

class function CLength.AsMillimetres(const Length: TFut): TMillimetre;
  begin
  Result := Length / UnitRatios[lunMM] end;

class function CLength.AsMillimetresString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsMillimetres(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunMM] end;

class function CLength.AsMillimetresStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsMillimetres(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunMM] end;

class function CLength.AsPicas(const Length: TFut): TPica;
  begin
  Result := Length / UnitRatios[lunPC] end;

class function CLength.AsPicasString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsPicas(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunPC] end;

class function CLength.AsPicasStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsPicas(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunPC] end;

class function CLength.AsPoint(const FutPoint: TFutPoint; const DeviceDpi: TDeviceUnit): TPoint;
  begin
  Result.X := AsDeviceUnits(FutPoint.X, DeviceDpi);
  Result.Y := AsDeviceUnits(FutPoint.Y, DeviceDpi) end;

class function CLength.AsPrintersPoints(const Length: TFut): TPrintersPoint;
  begin
  Result := Length / UnitRatios[lunPP] end;

class function CLength.AsPrintersPointsString(const Length: TFut): WideString;
  begin
  Result := FloatToStrF(AsPrintersPoints(Length), ffFixed, DoublePrecision, DisplayDecimalDigits) + UnitNames[lunPP] end;

class function CLength.AsPrintersPointsStringDigits(const Length: TFut; NumDigits: integer): WideString;
  begin
  Result := FloatToStrF(AsPrintersPoints(Length), ffFixed, DoublePrecision, NumDigits) + UnitNames[lunPP] end;

class function CLength.AsStringUnits(const Length: TFut; const Units: TUnit): WideString;
  begin
  if Length = NullFut then
    Result := ''
  else
    case Units of
      lunM:
        Result := AsMetresString(Length);
      lunIN:
        Result := AsInchesString(Length);
      lunCM:
        Result := AsCentimetresString(Length);
      lunPC:
        Result := AsPicasString(Length);
      lunMM:
        Result := AsMillimetresString(Length);
      lunPT:
        Result := AsBigPointsString(Length);
      lunPP:
        Result := AsPrintersPointsString(Length);
      lunFU:
        Result := AsFutsString(Length) end end;

class function CLength.AsStringUnitDigits(const Length: TFut; const Units: TUnit; NumDigits: integer): WideString;
  begin
  if Length = NullFut then
    Result := ''
  else
    case Units of
      lunM:
        Result := AsMetresStringDigits(Length, NumDigits);
      lunIN:
        Result := AsInchesStringDigits(Length, NumDigits);
      lunCM:
        Result := AsCentimetresStringDigits(Length, NumDigits);
      lunPC:
        Result := AsPicasStringDigits(Length, NumDigits);
      lunMM:
        Result := AsMillimetresStringDigits(Length, NumDigits);
      lunPT:
        Result := AsBigPointsStringDigits(Length, NumDigits);
      lunPP:
        Result := AsPrintersPointsStringDigits(Length, NumDigits);
      lunFU:
        Result := AsFutsString(Length) end end;

class function CLength.BigPoints(const Value: TBigPoint): TFut;
  begin
  Result := Round(Value * UnitRatios[lunPT]) end;

class function CLength.BigPoints(const Value: String): TFut;
  begin
  try
    Result := BigPoints(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.Centimetres(const Value: TCentimetre): TFut;
  begin
  Result := Round(Value * UnitRatios[lunCM]) end;

class function CLength.Centimetres(const Value: String): TFut;
  begin
  try
    Result := Centimetres(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.DevicePoint(const X, Y: TDeviceUnit): TDevicePoint;
  begin
  Result := TDevicePoint(point(x, y)) end;

class function CLength.DeviceRect(const Left, Top, Right, Bottom: TDeviceUnit): TDeviceRect;
  begin
  Result := TDeviceRect(rect(left, top, right, bottom)) end;

class function CLength.DeviceUnits(X: TDeviceUnit; DeviceDpi: TDeviceUnit): TFut;
  begin
  Result := Inches(X / DeviceDpi) end;

class function CLength.Dimension(const Value: String): TFut;
  const
    cPlus   = WideChar('+');
    cMinus  = WideChar('-');
    cPeriod = WideChar('.');
    cZero   = WideChar('0');
    cNine   = WideChar('9');
    DigitChars  = [cZero..cNine];
    SignChars   = [cPlus, cMinus];
    NumberChars = DigitChars + SignChars + [cPeriod];
  var
    I: Integer;
    SS: WideString;
    UnitName: WideString;
    Units: TUnit;
    ValueString: WideString;
  begin
  Result := 0;
  SS := Trim(Value);
  if SS = '' then begin
    Result := NullFut;
    Exit end;
  I := 1;
  while (I <= length(SS)) and (SS[I] in NumberChars) do
    Inc(I);
  ValueString := Copy(SS, 1, I - 1);
  UnitName := Trim(Copy(SS, I, Length(SS)));
  SS := LowerCase(UnitName);
  if SS = '' then
    Units := lunPT
  else begin
    Units := Low(TUnit);
    while (Units <= High(TUnit)) and (UnitNames[Units] <> SS) do
      Inc(Units);
    if Units > High(TUnit) then
      raise ELength.createFmt(sLengthInvalidUnits, [UnitName]) end;
  case Units of
    lunM:
      Result := Metres(ValueString);
    lunIN:
      Result := Inches(ValueString);
    lunCM:
      Result := Centimetres(ValueString);
    lunPC:
      Result := Picas(ValueString);
    lunMM:
      Result := Millimetres(ValueString);
    lunPT:
      Result := BigPoints(ValueString);
    lunPP:
      Result := PrintersPoints(ValueString);
    lunFU:
      Result := Futs(ValueString) end end;

class function CLength.FutPoint(const X, Y: TFut): TFutPoint;
  begin
  Result.X := X;
  Result.Y := Y end;

class function CLength.FutRect(const Left, Top, Right, Bottom: TFut): TFutRect;
  begin
  Result.Left := Left;
  Result.Top := Top;
  Result.Right := Right;
  Result.Bottom := Bottom end;

class function CLength.Futs(const Value: TFut): TFut;
  begin
  Result := Value end;

class function CLength.Futs(const Value: String): TFut;
  begin
  try
    Result := Futs(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]);
    on EInvalidOp do
      Result := 0 end end;

class function CLength.Inches(const Value: TInch): TFut;
  begin
  Result := Round(Value * UnitRatios[lunIN]) end;

class function CLength.Inches(const Value: String): TFut;
  begin
  try
    Result := Inches(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.Max(const A, B: TFut): TFut;
  begin
  if A > B then
    Result := A
  else
    Result := B end;

class function CLength.Metres(const Value: TMetre): TFut;
  begin
  Result := Round(Value * UnitRatios[lunM]) end;

class function CLength.Metres(const Value: String): TFut;
  begin
  try
    Result := Metres(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.Millimetres(const Value: TMillimetre): TFut;
  begin
  Result := Round(Value * UnitRatios[lunMM]) end;

class function CLength.Millimetres(const Value: String): TFut;
  begin
  try
    Result := Millimetres(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.Min(const A, B: TFut): TFut;
  begin
  if A < B then
    Result := A
  else
    Result := B end;

class function CLength.Percent(const Value: TPercent; const RefLength: TFut): TFut;
  begin
  Result := Round(Value * RefLength) end;

class function CLength.Percent(const Value: String; const RefLength: TFut): TFut;
  var
    S: WideString;
  begin
  S := Trim(Value);
  if S[Length(S)] = PERCENT_SIGN then begin
      SetLength(S, Length(S) - 1);
    try
      Result := Percent(0.01 * StrToFloat(S), RefLength);
    except
      on EConvertError do
        raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end
  else
    Result := Dimension(Value) end;

class function CLength.Picas(const Value: TPica): TFut;
  begin
  Result := Round(Value * UnitRatios[lunPC]) end;

class function CLength.Picas(const Value: String): TFut;
  begin
  try
    Result := Picas(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

class function CLength.PrintersPoints(const Value: TPrintersPoint): TFut;
  begin
  Result := Round(Value * UnitRatios[lunPP]) end;

class function CLength.PrintersPoints(const Value: String): TFut;
  begin
  try
    Result := PrintersPoints(StrToFloat(Value));
  except
    on EConvertError do
      raise ELength.createFmt(sLengthInvalidStringRep, [value]) end end;

//--TCTM-----------------------------------------------------------------------------------------------------------------------

type
  TCTM = class(TInterfacedObject, ICTM)
    private
      itsA: Double;
      itsB: Double;
      itsC: Double;
      itsD: Double;
      itsE: TFut;
      itsF: TFut;
      function trimFuzz(const value: Double): Double;
    protected
      function a: Double;
      function appendTo(const ctm: ICTM): ICTM;
      function apply(const p: TFutPoint): TFutPoint;
      function asString: WideString;
      function asXForm: TXForm;
      function b: Double;
      function c: Double;
      function d: Double;
      function e: TFut;
      function f: TFut;
      function prependTo(const ctm: ICTM): ICTM;
      function rotate(const theta: Double): ICTM;
      function scale(const s: Double): ICTM; overload;
      function scale(const sx, sy: Double): ICTM; overload;
      function skewX(const theta: Double): ICTM;
      function skewY(const theta: Double): ICTM;
      function translate(const t: TFut): ICTM; overload;
      function translate(const tx, ty: TFut): ICTM; overload;
    public
      constructor create;
      constructor createExplicit(const a, b, c, d: Double; const e, f: TFut); end;

constructor TCTM.create;
  begin
  createExplicit(1, 0, 0, 1, 0, 0) end;

constructor TCTM.createExplicit(const a, b, c, d: Double; const e, f: TFut);
  begin
  inherited create;
  itsA := trimFuzz(a);
  itsB := trimFuzz(b);
  itsC := trimFuzz(c);
  itsD := trimFuzz(d);
  itsE := trimFuzz(e);
  itsF := trimFuzz(f) end;

function TCTM.a: Double;
  begin
  Result := itsA end;

function TCTM.appendTo(const ctm: ICTM): ICTM;
  begin
  Result := TCTM.createExplicit( ctm.a * a + ctm.c * b + ctm.e * 0
                               , ctm.b * a + ctm.d * b + ctm.f * 0
                               , ctm.a * c + ctm.c * d + ctm.e * 0
                               , ctm.b * c + ctm.d * d + ctm.f * 0
                               , ctm.a * e + ctm.c * f + ctm.e * 1
                               , ctm.b * e + ctm.d * f + ctm.f * 1
                               ) end;

function TCTM.asString: WideString;
  begin
  Result := wideFormat('matrix(%g %g %g %g %g %g)', [a, b, c, d, e, f]) end;

function TCTM.asXForm: TXForm;
  begin
  Result.eM11 := a;
  Result.eM21 := b;
  Result.eM12 := c;
  Result.eM22 := d;
  Result.eDx := e;
  Result.eDy := f end;

function TCTM.b: Double;
  begin
  Result := itsB end;

function TCTM.c: Double;
  begin
  Result := itsC end;

function TCTM.d: Double;
  begin
  Result := itsD end;

function TCTM.e: TFut;
  begin
  Result := itsE end;

function TCTM.f: TFut;
  begin
  Result := itsF end;

function TCTM.apply(const p: TFutPoint): TFutPoint;
  begin
  Result.x := a * p.x + c * p.y + e;
  Result.y := b * p.x + d * p.y + f end;

function TCTM.prependTo(const ctm: ICTM): ICTM;
  begin
  Result := ctm.appendTo(Self) end;

function TCTM.rotate(const theta: Double): ICTM;
  var
    cs: Double;
    sn: Double;
  begin
  cs := cos(theta);
  sn := sin(theta);
  Result := prependTo(TCTM.createExplicit(cs, sn, -sn, cs, 0, 0)) end;

function TCTM.scale(const s: Double): ICTM;
  begin
  Result := scale(s, s) end;

function TCTM.scale(const sx, sy: Double): ICTM;
  begin
  Result := prependTo(TCTM.createExplicit(sx, 0, 0, sy, 0, 0)) end;

function TCTM.skewX(const theta: Double): ICTM;
  begin
  Result := prependTo(TCTM.createExplicit(1, 0, sin(theta) / cos(theta), 1, 0, 0)) end;

function TCTM.skewY(const theta: Double): ICTM;
  begin
  Result := prependTo(TCTM.createExplicit(1, sin(theta) / cos(theta), 0, 1, 0, 0)) end;

function TCTM.translate(const t: TFut): ICTM;
  begin
  Result := translate(t, t) end;

function TCTM.translate(const tx, ty: TFut): ICTM;
  begin
  Result := prependTo(TCTM.createExplicit(1, 0, 0, 1, tx, ty)) end;

function TCTM.trimFuzz(const value: Double): Double;
  begin
  Result := value;
  if abs(Result) < 1.0E-12 then
    Result := 0 end;

//-----------------------------------------------------------------------------------------------------------------------------

var
  singletonIdentityCTM: ICTM = nil;

function explicitCTM(const a, b, c, d: Double; const e, f: TFut): ICTM;
  begin
  Result := TCTM.createExplicit(a, b, c, d, e, f) end;

function identityCTM: ICTM;
  begin
  if not assigned(singletonIdentityCTM) then
    singletonIdentityCTM := TCTM.create;
  Result := singletonIdentityCTM end;

function getCTM(const canvas: TCanvas): ICTM;
  var
    xform: TXForm;
  begin
  if not getWorldTransform(canvas.handle, xform) then
    raiseLastOSError;
  Result := explicitCTM(xform.eM11, xform.eM21, xform.eM12, xform.eM22, xform.eDx, xform.eDy) end;

procedure initializeFuts(const canvas: TCanvas);
  begin
  if setGraphicsMode(canvas.handle, GM_ADVANCED) = 0 then
    raiseLastOSError;
  setCTM(canvas, explicitCTM(getDeviceCaps(canvas.handle, LOGPIXELSX) / UHRDPI, 0, 0,
    getDeviceCaps(canvas.handle, LOGPIXELSY) / UHRDPI, - getDeviceCaps(canvas.handle, PHYSICALOFFSETX),
    - getDeviceCaps(canvas.handle, PHYSICALOFFSETY))) end;

procedure setCTM(const canvas: TCanvas; const ctm: ICTM);
  begin
  if setGraphicsMode(canvas.handle, GM_ADVANCED) = 0 then
    raiseLastOSError;
  if not setWorldTransform(canvas.handle, ctm.asXform) then
    raiseLastOSError end;

initialization
  Move(NullLength, NullFut, SizeOf(NullFut));
finalization
  singletonIdentityCTM := nil end.

 */