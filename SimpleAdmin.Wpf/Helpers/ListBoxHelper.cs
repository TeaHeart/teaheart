namespace SimpleAdmin.Wpf.Helpers;

using System.Collections;
using System.Windows;
using System.Windows.Controls;

public static class ListBoxHelper
{
    public static readonly DependencyProperty SelectedItemsProperty =
        DependencyProperty.RegisterAttached(
            "SelectedItems",
            typeof(IList),
            typeof(ListBoxHelper),
            new FrameworkPropertyMetadata(null, OnSelectedItemsChanged)
        );

    public static IList GetSelectedItems(DependencyObject obj)
    {
        return (IList)obj.GetValue(SelectedItemsProperty);
    }

    public static void SetSelectedItems(DependencyObject obj, IList value)
    {
        obj.SetValue(SelectedItemsProperty, value);
    }

    private static void OnSelectedItemsChanged(
        DependencyObject d,
        DependencyPropertyChangedEventArgs e
    )
    {
        if (d is not ListBox listBox)
        {
            return;
        }

        listBox.SelectionChanged -= OnSelectionChanged;
        listBox.SelectionChanged += OnSelectionChanged;
    }

    private static void OnSelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (sender is not ListBox listBox)
        {
            return;
        }

        var selectedItems = GetSelectedItems(listBox);
        if (selectedItems == null)
        {
            return;
        }

        selectedItems.Clear();
        foreach (var item in listBox.SelectedItems)
        {
            selectedItems.Add(item);
        }
    }
}
