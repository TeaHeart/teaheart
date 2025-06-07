namespace SimpleAdmin.Wpf.ViewModels;

using System.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using SimpleAdmin.Wpf.Services;
using SimpleAdmin.Wpf.Services.Messages;

public partial class LogViewModel : INotifyPropertyChanged
{
    public string? LogText { get; private set; }
    private readonly ILogService logService;

    public LogViewModel(ILogService logService)
    {
        this.logService = logService;
        LogText = logService.Logs;
        WeakReferenceMessenger.Default.Register<LogUpdatedMessage>(this, OnLogUpdated);
    }

    ~LogViewModel()
    {
        WeakReferenceMessenger.Default.Unregister<LogUpdatedMessage>(this);
    }

    private void OnLogUpdated(object recipient, LogUpdatedMessage message)
    {
        LogText = message.Value;
    }

    [RelayCommand]
    private void Clear()
    {
        logService.Clear();
    }
}
