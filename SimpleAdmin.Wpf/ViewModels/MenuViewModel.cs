namespace SimpleAdmin.Wpf.ViewModels;

using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Controls;
using CommunityToolkit.Mvvm.Messaging;
using SimpleAdmin.Wpf.ViewModels.Messages;
using SimpleAdmin.Wpf.Views;

public partial class MenuViewModel : INotifyPropertyChanged
{
    public ObservableCollection<MenuItemViewModel> MenuItems { get; } = new();
    private MenuItemViewModel? selectedMenuItem;
    public MenuItemViewModel? SelectedMenuItem
    {
        get => selectedMenuItem;
        set
        {
            if (selectedMenuItem == value)
            {
                return;
            }
            selectedMenuItem = value;
            OnPropertyChanged();
            if (value != null)
            {
                WeakReferenceMessenger.Default.Send(
                    new AddTabMessage(
                        new()
                        {
                            Header = value.Header,
                            Content = value.Content,
                            ContentType = value.ContentType,
                        }
                    )
                );
            }
        }
    }

    public MenuViewModel()
    {
        MenuItems.Add(new MenuItemViewModel { Header = "欢迎", ContentType = typeof(WelcomeView) });
        MenuItems.Add(
            new MenuItemViewModel
            {
                Header = "关于",
                Content = new TextBlock { Text = "关于" },
            }
        );
    }
}
