namespace SimpleAdmin.Wpf.ViewModels;

using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Windows.Media;
using CommunityToolkit.Mvvm.Input;
using SimpleAdmin.Wpf.Extensions;
using SimpleAdmin.Wpf.Models;
using SimpleAdmin.Wpf.Services;

public partial class WelcomeViewModel : INotifyPropertyChanged
{
    public DateTime Now { get; set; }
    public int Counter { get; set; }
    public Gender Gender { get; set; }
    public ObservableCollection<Hobby> Hobbies { get; } = new();
    private readonly ILogService logService;

    public WelcomeViewModel(ILogService logService)
    {
        this.logService = logService;
        CompositionTarget.Rendering += CompositionTarget_Rendering;
    }

    ~WelcomeViewModel()
    {
        CompositionTarget.Rendering -= CompositionTarget_Rendering;
    }

    private void CompositionTarget_Rendering(object? sender, EventArgs e)
    {
        Now = DateTime.Now;
    }

    [RelayCommand]
    private void IncrementCounter()
    {
        Counter++;
    }

    [RelayCommand]
    private void Submit()
    {
        logService.Log(@$"性别: {Gender.ToDescription()}");
        logService.Log(@$"爱好: {string.Join(',', Hobbies.Select(x => x.ToDescription()))}");
    }
}
