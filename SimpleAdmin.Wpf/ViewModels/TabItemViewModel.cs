namespace SimpleAdmin.Wpf.ViewModels;

using System;
using System.ComponentModel;
using System.Windows;

public partial class TabItemViewModel : INotifyPropertyChanged
{
    public string? Header { get; set; }
    public UIElement? Content { get; set; }
    public Type? ContentType { get; set; }
}
