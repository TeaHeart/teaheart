namespace SimpleAdmin.Wpf.Models;

using System;
using System.ComponentModel;

[Flags]
public enum Hobby
{
    [Description("无")]
    None = 0,

    [Description("美食")]
    Food = 1,

    [Description("运动")]
    Sports = 2,

    [Description("编程")]
    Programming = 4,
}
