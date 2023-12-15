set terminal png
set title "Degree distribution"
set xlabel 'k'
set ylabel 'p(k)'
set output 'dd_dblp2.png'

unset logscale xy
# set logscale xy
set yrange [1e-6:0.2]
set xrange [1:400]


plot 'distribution.dat' title 'DBLP'
