# PercentBarView
PercentBar
```
 View alphaView = findViewById(R.id.SoruText);
 CustomAnswerPercent customAnswerPercent = (CustomAnswerPercent) findViewById(R.id.MyCustomAnswerPercent);
 customAnswerPercent.addAlphaView(alphaView);
 customAnswerPercent.setAValue(qf.getQuestion().getOption_B_Count());
 customAnswerPercent.setBValue(qf.getQuestion().getOption_A_Count());

 customAnswerPercent.showResult();
```

