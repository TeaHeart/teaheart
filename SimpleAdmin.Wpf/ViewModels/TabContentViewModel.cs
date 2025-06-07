namespace SimpleAdmin.Wpf.ViewModels;

using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Extensions.DependencyInjection;
using SimpleAdmin.Wpf.Extensions;
using SimpleAdmin.Wpf.ViewModels.Messages;
using SimpleAdmin.Wpf.Views;

public partial class TabContentViewModel : INotifyPropertyChanged
{
    public ObservableCollection<TabItemViewModel> TabItems { get; } = new();
    public int SelectedTabIndex { get; set; }
    private readonly IServiceProvider serviceProvider;

    public TabContentViewModel(IServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
        WeakReferenceMessenger.Default.Register<AddTabMessage>(this, OnAddTabMessage);
        AddTab(new TabItemViewModel { Header = "欢迎", ContentType = typeof(WelcomeView) });
    }

    ~TabContentViewModel()
    {
        WeakReferenceMessenger.Default.Unregister<AddTabMessage>(this);
    }

    private void OnAddTabMessage(object recipient, AddTabMessage message)
    {
        AddTab(message.Value);
    }

    private void AddTab(TabItemViewModel tabItem)
    {
        var index = TabItems.FindLastIndex(x => x.Header == tabItem.Header);
        if (index != -1)
        {
            SelectedTabIndex = index;
            return;
        }
        if (tabItem.Content == null && tabItem.ContentType != null)
        {
            tabItem.Content = (UIElement)serviceProvider.GetRequiredService(tabItem.ContentType);
        }
        TabItems.Add(tabItem);
        SelectedTabIndex = TabItems.Count - 1;
    }

    [RelayCommand]
    private void CloseTab(TabItemViewModel tab)
    {
        TabItems.Remove(tab);
    }
}
