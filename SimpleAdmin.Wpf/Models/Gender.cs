namespace SimpleAdmin.Wpf.Models;

using System.ComponentModel;

public enum Gender
{
    [Description("未设置")]
    None,

    [Description("男")]
    Male,

    [Description("女")]
    Female,
}
