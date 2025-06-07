namespace SimpleAdmin.Wpf.ViewModels;

using System.ComponentModel;
using SimpleAdmin.Wpf.Views;

public partial class MainViewModel : INotifyPropertyChanged
{
    public MenuView MenuView { get; }
    public TabContentView TabContentView { get; }
    public LogView LogView { get; }

    public MainViewModel(MenuView menuView, TabContentView tabContentView, LogView logView)
    {
        MenuView = menuView;
        TabContentView = tabContentView;
        LogView = logView;
    }
}
