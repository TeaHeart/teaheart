namespace SimpleAdmin.Wpf.Windows.Markup;

using System;
using System.Collections;
using System.Linq;
using System.Windows.Markup;

public class EnumValuesExtension : MarkupExtension
{
    public Type? EnumType { get; set; }
    public object? ExcludeValues { get; set; }

    public override object ProvideValue(IServiceProvider serviceProvider)
    {
        _ = EnumType ?? throw new NullReferenceException(nameof(EnumType));

        var excludeValuesList = ExcludeValues switch
        {
            IEnumerable enumerable => enumerable.Cast<Enum>(),
            Enum => new[] { (Enum)ExcludeValues },
            null => Enumerable.Empty<Enum>(),
            _ => throw new ArgumentException(nameof(ExcludeValues)),
        };

        var values = Enum.GetValues(EnumType).Cast<Enum>();

        if (!excludeValuesList.Any())
        {
            return values.ToList();
        }

        return values.Where(x => !excludeValuesList.Contains(x)).ToList();
    }
}
