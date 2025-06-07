namespace SimpleAdmin.Wpf.Extensions;

using System;
using System.ComponentModel;
using System.Reflection;

public static class EnumExtensions
{
    public static FieldInfo? GetField<TEnum>(this TEnum value)
        where TEnum : Enum
    {
        _ = value ?? throw new ArgumentNullException(nameof(value));
        var type = value.GetType();
        return type.GetField(value.ToString());
    }

    public static TAttribute? GetAttribute<TEnum, TAttribute>(this TEnum value)
        where TEnum : Enum
        where TAttribute : Attribute
    {
        _ = value ?? throw new ArgumentNullException(nameof(value));
        var field = value.GetField();
        return field?.GetCustomAttribute<TAttribute>(inherit: false);
    }

    public static string ToDescription<TEnum>(this TEnum value)
        where TEnum : Enum
    {
        _ = value ?? throw new ArgumentNullException(nameof(value));
        var desc = value.GetAttribute<TEnum, DescriptionAttribute>();
        return desc?.Description ?? value.ToString();
    }
}
