#!/usr/bin/env ruby
require 'Faker'

args = ARGV
valid_inputs = %w{firstname username password checking saving checking_balance saving_balance id lastname telephone email social salary employed employer citizen}

def is_numeric?(obj)
   obj.to_s.match(/\A[+-]?\d+?(\.\d+)?\Z/) == nil ? false : true
end
def is_file_name?(obj)
   obj.to_s.match(/\w+(\.\w+)\Z/) == nil ? false : true
end

#call this: (MAC)
#'./generate_data.rb 100 firstname lastname telephone email citizen employed employer BankApp/text_files/sample_unverified.txt'
#call this: (WINDOWS)
#./generate_data.rb 1000 username password firstname lastname telephone email citizen employed employer account_number balance account_number balance sample_data.csv
#dir = "/Users/christianmeyer/java/project-zero-Rakatashii/"
dir = "C:/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/"
#filename = "/Users/christianmeyer/java/project-zero-Rakatashii/data.txt"
filename = "C:/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/default.txt"

#1(means share account) + 2 digit share id + Member Number** = 13 digits
#**Member number needs to have proceeding zeros to fill the number out to 13 digits.

file_given = 0
if is_file_name?(ARGV[ARGV.size-1].to_s)
    filename = dir + ARGV[ARGV.size-1].to_s
    file_given = 1
end
 
args.each_with_index do |arg, i|
    if i == 0
        print "FORMAT (n = #{arg}): "
    end
    if i != 0 && i < args.size-2 
        print "#{arg}(#{i})|"
    elsif (i != 0 && i == args.size-2)
        print "#{arg}(#{i})\n"
    end
end
savings = [ ]
checking = [ ]
File.open(filename, 'w') { |f|
    Faker::Config.random.seed
    if (ARGV.size > 2)
        puts "generating data..."
    end
    n = 100
    number_given = 0
    if is_numeric?(args[0])
        n = args[0].to_i
        number_given = 1
    end
    id_idx = 0;
    n.times do
        num_args = ARGV.size - number_given - file_given
        print_line = "";
        args.any? {|arg|
            if arg == "id"
                id = id_idx
                id_idx++
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "username"
                username = Faker::Internet.username;
                print_line += username
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "password"
                password = Faker::Internet.password;
                print_line += password;
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "checking"
                account = (rand(1000000)+rand(10000000)).to_s
                print_line += account
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
                checking.push(account)
            end
            if arg == "saving"
                account = (rand(1000000)+rand(10000000)).to_s
                print_line += account
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
                savings.push(account)
            end
            if arg == "checking_balance"
                balance = rand(-10000.0..100000.0).round(2).to_s
                print_line += balance
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "saving_balance"
                balance = rand(-100000.0..1000000.0).round(2).to_s
                print_line += balance
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "firstname"
                    firstname = Faker::Name.first_name
                    print_line += firstname
                    num_args -= 1
                    if num_args > 0
                        print_line += "|"
                    end
            end
            if arg == "lastname"
                    lastname = Faker::Name.last_name
                    print_line += lastname
                    num_args -= 1
                    if num_args > 0
                        print_line += "|"
                    end
            end
            if arg == "telephone"
                t1 = ('%3.3s' % rand(000..999).to_s).gsub(' ', '0');
                t2 = ('%3.3s' % rand(000..999).to_s).gsub(' ', '0');
                t3 = ('%4.4s' % rand(000..999).to_s).gsub(' ', '0');
                tele = t1+"-"+t2+"-"+t3
                print_line += tele
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "email"
                email = Faker::Internet.email;
                print_line += email
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "salary"
                salary = "$" + rand(0..500000).to_s
                print_line += salary
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "social"
                s1 = ('%3.3s' % rand(000..999).to_s).gsub(' ', '0');
                s2 = ('%2.2s' % rand(000..999).to_s).gsub(' ', '0');
                s3 = ('%4.4s' % rand(000..999).to_s).gsub(' ', '0');
                social = s1+"-"+s2+"-"+s3
                print_line += social
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "citizen"
                citizen = rand(2)
                print_line += "#{citizen}"
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            employed = rand(2)
            if arg == "employed"
                print_line += "#{employed}"
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "employer"
                if employed != 0
                    company_name = Faker::Company.name
                    print_line += company_name
                else 
                    print_line += "null";
                end
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
            end
            if arg == "joint"
                joint = rand(4)
                print_line += "#{joint}"
                num_args -= 1
                if num_args > 0
                    print_line += "|"
                end
                if (joint == 1 && savings.size > 0) #P(joint) = 1/4
                    print_line += "|"
                    print_line += "#{savings[rand(savings.size)]}"
                else 
                    print_line += "|"
                    print_line += "null"
                end
            end
        }
        print_line += "\n"
        f.write(print_line)
    end
}
