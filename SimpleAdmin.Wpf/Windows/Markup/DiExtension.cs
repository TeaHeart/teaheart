namespace SimpleAdmin.Wpf.Windows.Markup;

using System;
using System.Windows.Markup;
using Microsoft.Extensions.DependencyInjection;

public class DiExtension : MarkupExtension
{
    public static IServiceProvider? ServiceProvider { get; set; }
    public Type? ServiceType { get; set; }

    public override object ProvideValue(IServiceProvider serviceProvider)
    {
        _ = ServiceProvider ?? throw new NullReferenceException(nameof(ServiceProvider));
        _ = ServiceType ?? throw new NullReferenceException(nameof(ServiceType));
        return ServiceProvider.GetRequiredService(ServiceType);
    }
}
