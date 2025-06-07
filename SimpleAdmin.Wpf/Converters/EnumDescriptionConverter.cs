namespace SimpleAdmin.Wpf.Converters;

using System;
using System.Globalization;
using System.Windows;
using System.Windows.Data;
using SimpleAdmin.Wpf.Extensions;

public class EnumDescriptionConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        if (value is Enum enumValue)
        {
            return enumValue.ToDescription();
        }
        return DependencyProperty.UnsetValue;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}
